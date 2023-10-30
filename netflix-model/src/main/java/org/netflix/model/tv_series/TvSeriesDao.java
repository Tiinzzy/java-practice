package org.netflix.model.tv_series;

import org.bson.Document;
import org.netflix.utility.Database;
import org.netflix.utility.OidGenerator;

import static com.mongodb.client.model.Filters.eq;

public class TvSeriesDao {
    private static final String TV_SERIES_COLLECTION = "tvshows";
    private long oid = -1;
    private String title = null;
    private String description = null;
    private String startDate = null;
    private String endDate = null;

    public TvSeriesDao() {
    }

    public TvSeriesDao(String title, String description, String startDate, String endDate) {
        this.oid = OidGenerator.getNew();
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TvSeriesDao(long oid) {
        this.oid = oid;
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(TV_SERIES_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.title = doc.getString("title");
            this.description = doc.getString("description");
            this.startDate = doc.getString("startDate");
            this.endDate = doc.getString("endDate");
        }
    }

    public void saveToTable(){
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("oid", this.oid);
        document.append("title", this.title);
        document.append("description", this.description);
        document.append("startDate", this.startDate);
        document.append("endDate", this.endDate);
        db.getCollection(TV_SERIES_COLLECTION).insertOne(document);
    }
    public long getOid() {
        return oid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
