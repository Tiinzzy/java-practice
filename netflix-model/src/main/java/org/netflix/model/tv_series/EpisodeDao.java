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

public class EpisodeDao {
    private static final String EPISODE_COLLECTION = "episodes";

    private long oid = -1;
    private String title = null;
    private int runTime = -1; // in minutes
    private String airDate = null;
    private long seasonOid = -1;

    private EpisodeDao() {
    }

    public EpisodeDao(String title, int runTime, String airDate, long seasonOid) {
        this.oid = OidGenerator.getNew();
        this.title = title;
        this.runTime = runTime;
        this.airDate = airDate;
        this.seasonOid = seasonOid;
    }

    public void saveToTable() {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("oid", this.oid);
        document.append("title", this.title);
        document.append("runTime", this.runTime);
        document.append("airDate", this.airDate);
        document.append("seasonOid", this.seasonOid);
        db.getCollection(EPISODE_COLLECTION).insertOne(document);
    }

    public EpisodeDao(long oid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(EPISODE_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.oid = oid;
            this.title = doc.getString("title");
            this.runTime = doc.getInteger("runTime");
            this.airDate = doc.getString("airDate");
            this.seasonOid = doc.getLong("seasonOid");
        }
    }

    public static List<EpisodeDao> loadAll(long seasonOid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        var docs = db.getCollection(EPISODE_COLLECTION).find(eq("seasonOid", seasonOid));
        var allEpisodesOfASeasonOid = new ArrayList<EpisodeDao>();
        for (var doc : docs) {
            EpisodeDao e = new EpisodeDao();
            e.oid = doc.getLong("oid");
            e.title = doc.getString("title");
            e.runTime = doc.getInteger("runTime");
            e.airDate = doc.getString("airDate");
            e.seasonOid = doc.getLong("seasonOid");
            allEpisodesOfASeasonOid.add(e);
        }
        return allEpisodesOfASeasonOid;
    }

    public static boolean delete(long oid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        MongoCollection<Document> collection = db.getCollection(EPISODE_COLLECTION);
        try {
            DeleteResult result = collection.deleteOne(eq("oid", oid));
            System.out.println("Deleted document count: " + result.getDeletedCount());
            return true;
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
            return false;
        }
    }

    public static boolean deleteSeasonEpisodes(long seasonOid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        MongoCollection<Document> collection = db.getCollection(EPISODE_COLLECTION);
        try {
            DeleteResult result = collection.deleteMany(eq("seasonOid", seasonOid));
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

    public int getRunTime() {
        return runTime;
    }

    public String getAirDate() {
        return airDate;
    }

    public long getSeasonOid() {
        return seasonOid;
    }
}
