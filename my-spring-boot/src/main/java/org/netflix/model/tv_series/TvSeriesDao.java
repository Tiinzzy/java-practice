package org.netflix.model.tv_series;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.netflix.utility.Database;
import org.netflix.utility.OidGenerator;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

public class TvSeriesDao {
    private static final String TV_SERIES_COLLECTION = "tv_series";
    private long oid = -1;
    private String title = null;
    private String summary = null;
    private String startDate = null;
    private String endDate = null;

    public TvSeriesDao() {
    }

    public TvSeriesDao(String title, String summary, String startDate, String endDate) {
        this.oid = OidGenerator.getNew();
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TvSeriesDao(long oid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(TV_SERIES_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.oid = oid;
            this.title = doc.getString("title");
            this.summary = doc.getString("summary");
            this.startDate = doc.getString("startDate");
            this.endDate = doc.getString("endDate");
        }
    }

    public void saveToTable() {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("title", this.title);
        document.append("summary", this.summary);
        document.append("startDate", this.startDate);
        document.append("endDate", this.endDate);
        document.append("oid", this.oid);
        if (TvSeriesDao.oidExist(this.oid)) {
            db.getCollection(TV_SERIES_COLLECTION).replaceOne(eq("oid", this.oid), document);
            // Bson update = combine(set("title", this.title),set("summary", this.summary));
            // db.getCollection(TV_SERIES_COLLECTION).updateOne(eq("oid", this.oid), update);
        } else {
            db.getCollection(TV_SERIES_COLLECTION).insertOne(document);
        }
    }

    private static boolean oidExist(long oid) {
        TvSeriesDao temp = new TvSeriesDao(oid);
        return temp.getOid() == oid;
    }

    public static List<TvSeriesDao> loadAll() {
        return loadAll("", 100);
    }

    public static List<TvSeriesDao> loadAll(int count) {
        return loadAll("", count);
    }

    public static List<TvSeriesDao> loadAll(String nameContains, int count) {
        var db = Database.INSTANCE.getNetflixDatabase();
        var docs = db.getCollection(TV_SERIES_COLLECTION).find(regex("title", nameContains, "i")).limit(count);
        var allTvSeries = new ArrayList<TvSeriesDao>();
        for (var doc : docs) {
            TvSeriesDao tv = new TvSeriesDao();
            tv.oid = doc.getLong("oid");
            tv.title = doc.getString("title");
            tv.summary = doc.getString("summary");
            tv.startDate = doc.getString("startDate");
            tv.endDate = doc.getString("endDate");
            allTvSeries.add(tv);
        }
        return allTvSeries;
    }

    public List<SeasonDao> loadSeasons() {
        return SeasonDao.loadAll(this.oid);
    }

    public void addSeason(int seasonNumber, String startDate, String endDate) {
        SeasonDao seasonDao = new SeasonDao(seasonNumber, startDate, endDate, this.oid);
        seasonDao.saveToTable();
    }

    public void addEpisode(int seasonNumber, String title, int runTime, String airDate) {
        var season = getSeason(seasonNumber);
        if (season != null) {
            season.addEpisode(title, runTime, airDate);
        }
    }

    public SeasonDao getSeason(int seasonNumber) {
        var seasons = this.loadSeasons();
        for (var s : seasons) {
            if (s.getSeasonNumber() == seasonNumber) {
                return s;
            }
        }
        return null;
    }

    public boolean deleteSeason(long seasonOid) {
        return SeasonDao.delete(seasonOid);
    }

    public static boolean deleteSeasons(long tvSeriesOid) {
        List<SeasonDao> seasons = SeasonDao.loadAll(tvSeriesOid);
        for (var s : seasons) {
            if (s.getTvSeriesOid() == tvSeriesOid) {
                if (!SeasonDao.delete(s.getOid())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean delete(long oid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        MongoCollection<Document> collection = db.getCollection(TV_SERIES_COLLECTION);
                try {
                    TvSeriesDao.deleteSeasons(oid);
                    DeleteResult result = collection.deleteOne(eq("oid", oid));
                    System.out.println("Deleted document count: " + result.getDeletedCount());
                    return true;
                } catch (MongoException me) {
                    System.err.println("Unable to delete due to an error: " + me);
                    return false;
                }
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
