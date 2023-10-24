package org.netflix.model.subscription;

import org.bson.Document;
import org.netflix.model.movies.MovieDao;
import org.netflix.utility.Database;
import org.netflix.utility.OidGenerator;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class SubscriptionDao {
    private static final String SUBSCRIPTION_COLLECTION = "subscription";
    private long oid = -1;
    private String subscriptionType = null;
    private String price = null;
    private String expiryDate = null;
    private String subscriptionDate = null;

    public SubscriptionDao() {
    }

    public SubscriptionDao(String subscriptionType, String price, String expiryDate, String subscriptionDate) {
        this.oid = OidGenerator.getNew();
        this.subscriptionType = subscriptionType;
        this.price = price;
        this.expiryDate = expiryDate;
        this.subscriptionDate = subscriptionDate;
    }

    public SubscriptionDao(long oid) {
        this.oid = oid;
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(SUBSCRIPTION_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.subscriptionType = doc.getString("subscriptionType");
            this.price = doc.getString("price");
            this.expiryDate = doc.getString("expiryDate");
            this.subscriptionDate = doc.getString("subscriptionDate");
        }
    }

    public void saveToTable(){
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("oid", this.oid);
        document.append("subscriptionType", this.subscriptionType);
        document.append("price", this.price);
        document.append("expiryDate", this.expiryDate);
        document.append("subscriptionDate", this.subscriptionDate);
        db.getCollection(SUBSCRIPTION_COLLECTION).insertOne(document);
    }

    public static List<SubscriptionDao> loadAll(){
        var db = Database.INSTANCE.getNetflixDatabase();
        var docs = db.getCollection(SUBSCRIPTION_COLLECTION).find();
        List<SubscriptionDao> allSubscriptionDao = new ArrayList<>();
        for (var doc : docs) {
            SubscriptionDao sd = new SubscriptionDao();
            sd.oid = doc.getLong("oid");
            sd.subscriptionType = doc.getString("subscriptionType");
            sd.price = doc.getString("price");
            sd.expiryDate = doc.getString("expiryDate");
            sd.subscriptionDate = doc.getString("subscriptionDate");
            allSubscriptionDao.add(sd);
        }
        return allSubscriptionDao;
    }

    public long getOid() {
        return oid;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public String getPrice() {
        return price;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }
}
