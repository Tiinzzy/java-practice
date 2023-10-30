package org.netflix.model.tv_series;

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
        document.append("oid", this.oid);
        document.append("title", this.title);
        document.append("summary", this.summary);
        document.append("startDate", this.startDate);
        document.append("endDate", this.endDate);
        db.getCollection(TV_SERIES_COLLECTION).insertOne(document);
    }

    // Todo: Finish this tomorrow. So that loadAll("thing") returns at least "Stranger Things" and "The Thing", ...
    public List<TvSeriesDao> loadAll(String nameContains, int count) {
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
