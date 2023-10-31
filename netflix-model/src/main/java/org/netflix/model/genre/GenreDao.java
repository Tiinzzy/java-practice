package org.netflix.model.genre;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
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
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(GENRE_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.oid = oid;
            this.description = doc.getString("description");
        }
    }

    public void saveToTable() {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("oid", this.oid);
        document.append("description", this.description);
        if (GenreDao.oidExist(this.oid)) {
            db.getCollection(GENRE_COLLECTION).replaceOne(eq("oid", this.oid), document);
        } else {
            db.getCollection(GENRE_COLLECTION).insertOne(document);
        }
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

    public void deleteGenre(long oid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        MongoCollection<Document> collection = db.getCollection(GENRE_COLLECTION);
        try {
            DeleteResult result = collection.deleteOne(eq("oid", oid));
            System.out.println("Deleted document count: " + result.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }
    }

    private static boolean oidExist(long oid) {
        GenreDao temp = new GenreDao(oid);
        return temp.getOid() == oid;
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
