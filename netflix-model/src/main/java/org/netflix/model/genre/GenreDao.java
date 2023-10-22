package org.netflix.model.genre;

import org.bson.Document;
import org.netflix.utility.Database;
import org.netflix.utility.OidGenerator;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class GenreDao {
    private static final String GENRE_COLLECTION = "genre";
    private long oid = -1;
    private String description = null;

    private GenreDao() {
    }

    public GenreDao(String description) {
        this.description = description;
        this.oid = OidGenerator.getNew();
    }

    public GenreDao(long oid) {
        this.oid = oid;
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(GENRE_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.description = doc.getString("description");
        }
    }

    public void saveToTable() {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("oid", this.oid);
        document.append("description", this.description);
        db.getCollection(GENRE_COLLECTION).insertOne(document);
    }

    public static List<GenreDao> loadAll() {
        var db = Database.INSTANCE.getNetflixDatabase();
        var docs = db.getCollection(GENRE_COLLECTION).find();
        List<GenreDao> allGenreDao = new ArrayList<>();
        for (var doc : docs) {
            GenreDao genre = new GenreDao();
            genre.oid = doc.getLong("oid");
            genre.description = doc.getString("description");
            allGenreDao.add(genre);
        }
        return allGenreDao;
    }

    public long getOid() {
        return oid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
