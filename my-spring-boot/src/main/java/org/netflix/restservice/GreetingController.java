package org.netflix.restservice;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.netflix.model.customer.CustomerDao;
import org.netflix.model.genre.GenreDao;
import org.netflix.model.movies.MovieDao;
import org.netflix.model.subscription.SubscriptionDao;
import org.netflix.model.tv_series.TvSeriesDao;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
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
        if (loadNew.getOid() == newGenre.getOid()) {
            return true;
        }
        return false;
    }

    static class MyGenreData {
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
        if (loadNew.getOid() == newCustomer.getOid()) {
            return true;
        }
        return false;
    }

    static class MyCustomerData {

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
        if (loadNew.getOid() == newMovie.getOid()) {
            return true;
        }
        return false;
    }

    static class MyMovieData {
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

}
