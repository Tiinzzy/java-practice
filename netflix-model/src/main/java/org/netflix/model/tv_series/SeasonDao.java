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

public class SeasonDao {
    private static final String SEASON_COLLECTION = "seasons";
    private long oid = -1;
    private int seasonNumber = -1;
    private String startDate = null;
    private String endDate = null;

    private long tvSeriesOid = -1;

    public SeasonDao() {
    }

    public SeasonDao(int seasonNumber, String startDate, String endDate, long tvSeriesOid) {
        this.oid = OidGenerator.getNew();
        this.seasonNumber = seasonNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tvSeriesOid = tvSeriesOid;
    }

    public SeasonDao(long oid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(SEASON_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.oid = oid;
            this.seasonNumber = doc.getInteger("seasonNumber");
            this.startDate = doc.getString("startDate");
            this.endDate = doc.getString("endDate");
            this.tvSeriesOid = doc.getLong("tvSeriesOid");
        }
    }

    public void saveToTable() {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("oid", this.oid);
        document.append("seasonNumber", this.seasonNumber);
        document.append("startDate", this.startDate);
        document.append("endDate", this.endDate);
        document.append("tvSeriesOid", this.tvSeriesOid);
        db.getCollection(SEASON_COLLECTION).insertOne(document);
    }

    public static List<SeasonDao> loadAll(long tvSeriesOid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        var docs = db.getCollection(SEASON_COLLECTION).find(eq("tvSeriesOid", tvSeriesOid));
        var allSeasonsOfATvSeriesOid = new ArrayList<SeasonDao>();
        for (var doc : docs) {
            SeasonDao e = new SeasonDao();
            e.oid = doc.getLong("oid");
            e.seasonNumber = doc.getInteger("seasonNumber");
            e.startDate = doc.getString("startDate");
            e.endDate = doc.getString("endDate");
            e.tvSeriesOid = doc.getLong("tvSeriesOid");
            allSeasonsOfATvSeriesOid.add(e);
        }
        return allSeasonsOfATvSeriesOid;
    }

    public List<EpisodeDao> loadEpisodes() {
        return EpisodeDao.loadAll(this.oid);
    }

    public void addEpisode(String title, int runTime, String airDate) {
        EpisodeDao episodeDao = new EpisodeDao(title, runTime, airDate, this.oid);
        episodeDao.saveToTable();
    }

    // Todo:
    public void deleteEpisode(long episodeOid) {
        EpisodeDao.delete(episodeOid);
    }

    // Todo:
    public boolean deleteEpisodes() {
        List<SeasonDao> seasons = SeasonDao.loadAll(this.getOid());
        for (var s : seasons) {
            if (!EpisodeDao.deleteSeasonEpisodes(s.getOid())) {
                return false;
            }
        }
        return true;
    }

    public static boolean delete(long seasonOid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        MongoCollection<Document> collection = db.getCollection(SEASON_COLLECTION);
        try {
            EpisodeDao.deleteSeasonEpisodes(seasonOid);
            DeleteResult result = collection.deleteOne(eq("oid", seasonOid));
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

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public long getTvSeriesOid() {
        return tvSeriesOid;
    }
}
