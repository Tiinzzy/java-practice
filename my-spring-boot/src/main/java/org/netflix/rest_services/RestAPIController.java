package org.netflix.rest_services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.netflix.ant_simulation.LangstonAnt;
import org.netflix.directory_depth.DirectoryTree;
import org.netflix.game_of_life.Board;
import org.netflix.game_of_life.Torus;
import org.netflix.gravity.Universe;
import org.netflix.model.customer.CustomerDao;
import org.netflix.model.genre.GenreDao;
import org.netflix.model.movies.MovieDao;
import org.netflix.model.subscription.EPrice;
import org.netflix.model.subscription.ESubscriptionType;
import org.netflix.model.subscription.SubscriptionDao;
import org.netflix.model.tv_series.SeasonDao;
import org.netflix.model.tv_series.TvSeriesDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RestAPIController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    Board golBoard = null;
    Universe universe = null;

    LangstonAnt langstonAnt = null;

    @GetMapping("/greeting")
    public RestAPI greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new RestAPI(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/genre/{id}")
    public GenreDao genre(@PathVariable(required = true) long id) {
        return new GenreDao(id);
    }

    @GetMapping("/genre/all")
    public List<GenreDao> allGenre() {
        return GenreDao.loadAll();
    }

    @GetMapping("/customer/all")
    public List<CustomerDao> allCustomers() {
        return CustomerDao.loadAll();
    }

    @GetMapping("/movie/all")
    public List<MovieDao> allMovies() {
        return MovieDao.loadAll();
    }

    @GetMapping("/subscription/all")
    public List<SubscriptionDao> allSubscriptions() {
        return SubscriptionDao.loadAll();
    }

    @GetMapping("/tvseries/all")
    public List<TvSeriesDao> allTvSeries() {
        return TvSeriesDao.loadAll();
    }

    @PostMapping("/genre/add")
    public Boolean addGenre(@RequestBody MyGenreData data) {
        GenreDao newGenre = new GenreDao(data.getDescription());
        newGenre.saveToTable();

        GenreDao loadNew = new GenreDao(newGenre.getOid());
        return (loadNew.getOid() == newGenre.getOid());
    }

    public static class MyGenreData {
        private long oid;

        private String description;

        public long getOid() {
            return oid;
        }

        public String getDescription() {
            return description;
        }
    }

    @PostMapping("/customer/add")
    public Boolean addCustomer(@RequestBody MyCustomerData data) {
        CustomerDao newCustomer = new CustomerDao(data.getName(), data.getPhoneNo(), data.getEmail());
        newCustomer.saveToTable();

        CustomerDao loadNew = new CustomerDao(newCustomer.getOid());
        return loadNew.getOid() == newCustomer.getOid();
    }

    public static class MyCustomerData {

        private long oid;
        private String name;
        private String phoneNo;
        private String email;

        public String getName() {
            return name;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public String getEmail() {
            return email;
        }

        public long getOid() {
            return oid;
        }
    }

    @PostMapping("/movie/add")
    public Boolean addMovie(@RequestBody MyMovieData data) {
        MovieDao newMovie = new MovieDao(data.getMovieTitle(), data.getReleaseDate(), data.getRating());
        newMovie.saveToTable();

        MovieDao loadNew = new MovieDao(newMovie.getOid());
        return (loadNew.getOid() == newMovie.getOid());
    }

    public static class MyMovieData {
        private long oid;
        private String movieTitle;
        private String releaseDate;
        private String rating;

        public String getMovieTitle() {
            return movieTitle;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public String getRating() {
            return rating;
        }

        public long getOid() {
            return oid;
        }
    }

    @PostMapping("/customer/delete")
    public Boolean deleteCustomer(@RequestBody MyCustomerData data) {
        CustomerDao removeCustomer = new CustomerDao(data.getOid());
        removeCustomer.delete(data.getOid());

        return true;
    }

    @PostMapping("/genre/delete")
    public Boolean deleteGenre(@RequestBody MyGenreData data) {
        GenreDao removeGenre = new GenreDao(data.getOid());
        removeGenre.delete(data.getOid());

        return true;
    }

    @PostMapping("/movie/delete")
    public Boolean deleteMovie(@RequestBody MyMovieData data) {
        MovieDao removeMovie = new MovieDao(data.getOid());
        removeMovie.delete(data.getOid());

        return true;
    }

    @PostMapping("/subscription/add")
    public Boolean addSubscription(@RequestBody MySubscriptionData data) {
        SubscriptionDao newSub = new SubscriptionDao(data.getSubscriptionType(), data.getPrice(), data.getSubscriptionDate(), data.getExpiryDate());
        newSub.saveToTable();

        SubscriptionDao loadNew = new SubscriptionDao(newSub.getOid());
        return loadNew.getOid() == newSub.getOid();
    }

    public static class MySubscriptionData {
        private long oid;
        private ESubscriptionType subscriptionType;
        private EPrice price;
        private String expiryDate;
        private String subscriptionDate;

        public long getOid() {
            return oid;
        }

        public ESubscriptionType getSubscriptionType() {
            return subscriptionType;
        }

        public EPrice getPrice() {
            return price;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public String getSubscriptionDate() {
            return subscriptionDate;
        }
    }

    @PostMapping("/subscription/delete")
    public Boolean deleteSubscription(@RequestBody MySubscriptionData data) {
        SubscriptionDao removeSub = new SubscriptionDao(data.getOid());
        removeSub.delete(data.getOid());

        return true;
    }

    @PostMapping("/customer/update")
    public Boolean updateCustomer(@RequestBody MyCustomerData data) {
        CustomerDao reloadCustomer = new CustomerDao(data.getOid());
        reloadCustomer.setName(data.getName());
        reloadCustomer.setEmail(data.getEmail());
        reloadCustomer.setPhoneNo(data.phoneNo);
        reloadCustomer.saveToTable();
        return true;
    }

    @PostMapping("/genre/update")
    public Boolean updateGenre(@RequestBody MyGenreData data) {
        GenreDao reloadGenre = new GenreDao(data.getOid());
        reloadGenre.setDescription(data.getDescription());
        reloadGenre.saveToTable();
        return true;
    }

    @PostMapping("/movie/update")
    public Boolean updateMovie(@RequestBody MyMovieData data) {
        MovieDao reloadMovie = new MovieDao(data.getOid());
        reloadMovie.setMovieTitle(data.getMovieTitle());
        reloadMovie.setRating(data.getRating());
        reloadMovie.setReleaseDate(data.getReleaseDate());
        reloadMovie.saveToTable();
        return true;
    }

    @PostMapping("/subscription/update")
    public Boolean updateSubscription(@RequestBody MySubscriptionData data) {
        SubscriptionDao reloadSub = new SubscriptionDao(data.getOid());
        reloadSub.setExpiryDate(data.getExpiryDate());
        reloadSub.setPrice(data.getPrice());
        reloadSub.setSubscriptionType(data.getSubscriptionType());
        reloadSub.setSubscriptionDate(data.getSubscriptionDate());
        reloadSub.saveToTable();
        return true;
    }

    @PostMapping("/tvseries/add")
    public Boolean addTvSeries(@RequestBody MyTvSeriesData data) {
        TvSeriesDao newTvSeries = new TvSeriesDao(data.getTitle(), data.getSummary(), data.getStartDate(), data.getEndDate());
        newTvSeries.saveToTable();
        TvSeriesDao loadNew = new TvSeriesDao(newTvSeries.getOid());

        if (data.getAction().equals("addSeason")) {
            SeasonDao newSeason = new SeasonDao(data.getSeasonNumber(), data.getSeasonStartDate(), data.getSeasonEndDate(), newTvSeries.getOid());
            newSeason.saveToTable();
            SeasonDao loadNewSeason = new SeasonDao(newSeason.getOid());
            if (data.getSeasonNumber() > 0) {
                return loadNew.getOid() == newTvSeries.getOid() & loadNewSeason.getOid() == newSeason.getOid();
            }
        }
        return loadNew.getOid() == newTvSeries.getOid();
    }

    @PostMapping("/tvseries/load-seasons")
    public List<SeasonDao> loadTvSeriesSeasons(@RequestBody MyTvSeriesData data) {
        TvSeriesDao loadTvSeries = new TvSeriesDao(data.getOid());
        return loadTvSeries.loadSeasons();
    }

    @PostMapping("/tvseries/delete")
    public Boolean deleteTvSeries(@RequestBody MyTvSeriesData data) {
        TvSeriesDao.delete(data.getOid());
        return true;
    }

    @PostMapping("/tvseries/update")
    public Boolean updateTvSeries(@RequestBody MyTvSeriesData data) {
        System.out.println(data.getAction());
        TvSeriesDao loadTvSeries = new TvSeriesDao(data.getOid());
        loadTvSeries.setTitle(data.getTitle());
        loadTvSeries.setSummary(data.getSummary());
        loadTvSeries.setStartDate(data.getStartDate());
        loadTvSeries.setEndDate(data.getEndDate());
        loadTvSeries.saveToTable();

        if (data.getSeasonNumber() > 0 & data.getAction().equals("addNew")) {
            System.out.println("here to add new");
            SeasonDao newSeason = new SeasonDao(data.getSeasonNumber(), data.getSeasonStartDate(), data.getSeasonEndDate(), loadTvSeries.getOid());
            newSeason.saveToTable();

            SeasonDao loadNewSeason = new SeasonDao(newSeason.getOid());
            return loadNewSeason.getOid() == newSeason.getOid();
        } else if (data.getSeasonNumber() > 0 & data.getAction().equals("update")) {
            System.out.println("here to update");
            SeasonDao updateSeason = new SeasonDao(data.getSeasonOid());
            updateSeason.setSeasonNumber(data.getSeasonNumber());
            updateSeason.setEndDate(data.getSeasonEndDate());
            updateSeason.setStartDate(data.getSeasonStartDate());
            updateSeason.saveToTable();
        }
        return true;
    }

    public static class MyTvSeriesData {
        private long oid;
        private String title;
        private String summary;
        private String startDate;
        private String endDate;
        private int seasonNumber;

        private String action;

        private String seasonStartDate;
        private String seasonEndDate;

        private long seasonOid;

        public long getSeasonOid() {
            return seasonOid;
        }

        public int getSeasonNumber() {
            return seasonNumber;
        }

        public String getAction() {
            return action;
        }

        public String getSeasonStartDate() {
            return seasonStartDate;
        }

        public String getSeasonEndDate() {
            return seasonEndDate;
        }

        public long getOid() {
            return oid;
        }

        public String getTitle() {
            return title;
        }

        public String getSummary() {
            return summary;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }
    }

    @PostMapping("/directory/path")
    public String directoryPathSearch(@RequestBody MyDirectoryData data) {
        DirectoryTree tree = new DirectoryTree(data.getPath());
        tree.buildTree(data.getDepth());
        tree.saveJsonToFile();
        return tree.toString();
    }

    public static class MyDirectoryData {
        private String path;
        private int depth;

        public String getPath() {
            return path;
        }

        public int getDepth() {
            return depth;
        }
    }

    Map<Integer, String> generations = new ConcurrentHashMap<>();

    @GetMapping("/generation/{id}")
    public ResponseEntity<String> getGeneration(@PathVariable int id) {
        if (generations.containsKey(id)) {
            return ResponseEntity.ok(generations.get(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/gol/init")
    public String golInit(@RequestBody MyGameData data) {
        golBoard = new Torus(data.getRow(), data.getColumn());
        golBoard.initialize(data.getInitCount());
        return golBoard.toJSON().toString();
    }

    @GetMapping("/gol/evolve")
    public String golEvolve() {
        if (golBoard == null) {
            return "{}";
        } else {
            golBoard.evolve();
            System.out.println(golBoard.toJSON().toString());
            return golBoard.toJSON().toString();
        }
    }

    @PostMapping("/gol/update")
    public String golUpdateGrid(@RequestBody String data) {
        JSONObject jData = new JSONObject(data);

        golBoard = new Torus(jData.optInt("row", 10), jData.optInt("column", 10));
        JSONArray jGrid = jData.getJSONArray("grid");

        for (int r = 0; r < golBoard.getRowCount(); r++) {
            JSONArray row = jGrid.getJSONArray(r);
            for (int c = 0; c < golBoard.getColumnCount(); c++) {
                int cellValue = row.getInt(c);
                if (cellValue == 1) {
                    golBoard.setCell(r, c);
                }
            }
        }
        System.out.println("------------------");
        golBoard.display();
        System.out.println("------------------");

        return golBoard.toJSON().toString();
    }

    public static class MyGameData {
        private int row;
        private int column;

        private int initCount;

        private int generations;

        private String grid;

        public String getGrid() {
            return grid;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public int getInitCount() {
            return initCount;
        }

        public int getGenerations() {
            return generations;
        }
    }

    @GetMapping("/gravity/init/size/{spaceSize}/count/{count}")
    public String gravityInitSpace(@PathVariable int spaceSize, @PathVariable int count) {
        universe = new Universe(spaceSize);
        universe.init(count);
        return universe.toJSON().toString();
    }

    @GetMapping("/gravity/tick")
    public String gravityTick() {
        if (universe == null) {
            universe = new Universe(100);
            universe.init(50);
        }
        universe.tick();
        return universe.toJSON().toString();
    }

    @GetMapping("/langtons-ant/tick/board/{boardSize}/steps/{steps}/ants/{ants}")
    public String getNextHouse(@PathVariable int boardSize, @PathVariable int steps, @PathVariable int ants) {
        System.out.println(LocalDateTime.now().toString() + "   " + steps);
        if (langstonAnt == null) {
            langstonAnt = new LangstonAnt(boardSize, ants);
        }

        for (int i = 0; i < steps - 1; i++) {
            langstonAnt.nextMove();
        }

        return langstonAnt.nextMove().toString();
    }

    @GetMapping("/langtons-ant/reset")
    public String resetLangtonGrid() {
        langstonAnt = null;
        JSONObject result = new JSONObject();
        result.put("grid", "reset");
        return result.toString();
    }
}
