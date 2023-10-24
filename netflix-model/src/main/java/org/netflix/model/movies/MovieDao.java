package org.netflix.model.movies;

import org.bson.Document;
import org.netflix.model.customer.CustomerDao;
import org.netflix.utility.Database;
import org.netflix.utility.OidGenerator;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MovieDao {
    private static final String MOVIE_COLLECTION = "movie";
    private long oid = -1;
    private String movieTitle = null;
    private String releaseDate = null;
    private String rating = null;

    public MovieDao() {
    }

    public MovieDao(String movieTitle, String releaseDate, String rating) {
        this.oid = OidGenerator.getNew();
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public MovieDao(long oid) {
        this.oid = oid;
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(MOVIE_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.movieTitle = doc.getString("movieTitle");
            this.releaseDate = doc.getString("releaseDate");
            this.rating = doc.getString("rating");
        }
    }

    public void saveToTable(){
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("oid", this.oid);
        document.append("movieTitle", this.movieTitle);
        document.append("releaseDate", this.releaseDate);
        document.append("rating", this.rating);
        db.getCollection(MOVIE_COLLECTION).insertOne(document);
    }

    public static List<MovieDao> loadAll(){
        var db = Database.INSTANCE.getNetflixDatabase();
        var docs = db.getCollection(MOVIE_COLLECTION).find();
        List<MovieDao> allMovieDao = new ArrayList<>();
        for (var doc : docs) {
            MovieDao md = new MovieDao();
            md.oid = doc.getLong("oid");
            md.movieTitle = doc.getString("movieTitle");
            md.releaseDate = doc.getString("releaseDate");
            md.rating = doc.getString("rating");
            allMovieDao.add(md);
        }
        return allMovieDao;
    }

    public long getOid() {
        return oid;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRating() {
        return rating;
    }
}
