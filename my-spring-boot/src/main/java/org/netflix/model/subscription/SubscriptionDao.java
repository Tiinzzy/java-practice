package org.netflix.model.subscription;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.netflix.utility.Database;
import org.netflix.utility.OidGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class SubscriptionDao {
    private static final String SUBSCRIPTION_COLLECTION = "subscription";
    private long oid = -1;
    private ESubscriptionType subscriptionType = ESubscriptionType.NOT_DEFINED;
    private EPrice price = EPrice.NOT_DEFINED;
    private String expiryDate = null;
    private String subscriptionDate = null;

    public static Map<EPrice, Double> getPrices() {
        Map<EPrice, Double> enumToPrice = new HashMap<>();
        enumToPrice.put(EPrice.SINGLE_USER, 10.990);
        enumToPrice.put(EPrice.MULTI_USER, 12.99);
        enumToPrice.put(EPrice.MULTI_4K, 16.99);
        return enumToPrice;
    }

    public static Double getPrices(EPrice ep) {
        Map<EPrice, Double> enumToPrice = getPrices();
        return enumToPrice.get(ep);
    }

    private SubscriptionDao() {
    }

    public SubscriptionDao(ESubscriptionType subscriptionType, EPrice price, String subscriptionDate, String expiryDate) {
        this.oid = OidGenerator.getNew();
        this.subscriptionType = subscriptionType;
        this.price = price;
        this.expiryDate = expiryDate;
        this.subscriptionDate = subscriptionDate;
    }

    public SubscriptionDao(long oid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(SUBSCRIPTION_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.oid = oid;
            this.subscriptionType = ESubscriptionType.valueOf(doc.getString("subscriptionType"));
            this.price = EPrice.valueOf(doc.getString("price"));
            this.expiryDate = doc.getString("expiryDate");
            this.subscriptionDate = doc.getString("subscriptionDate");
        }
    }

    public void saveToTable() {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("oid", this.oid);
        document.append("subscriptionType", this.subscriptionType.toString());
        document.append("price", this.price.toString());
        document.append("expiryDate", this.expiryDate);
        document.append("subscriptionDate", this.subscriptionDate);
        if (SubscriptionDao.oidExist(this.oid)) {
            db.getCollection(SUBSCRIPTION_COLLECTION).replaceOne(eq("oid", this.oid), document);
        } else {
            db.getCollection(SUBSCRIPTION_COLLECTION).insertOne(document);
        }
    }

    public void delete(long oid){
        var db = Database.INSTANCE.getNetflixDatabase();
        MongoCollection<Document> collection = db.getCollection(SUBSCRIPTION_COLLECTION);
        try {
            DeleteResult result = collection.deleteOne(eq("oid", oid));
            System.out.println("Deleted document count: " + result.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }
    }

    public void setSubscriptionType(ESubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public void setPrice(EPrice price) {
        this.price = price;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public static List<SubscriptionDao> loadAll() {
        var db = Database.INSTANCE.getNetflixDatabase();
        var docs = db.getCollection(SUBSCRIPTION_COLLECTION).find();
        List<SubscriptionDao> allSubscriptionDao = new ArrayList<>();
        for (var doc : docs) {
            SubscriptionDao sd = new SubscriptionDao();
            sd.oid = doc.getLong("oid");
            sd.subscriptionType = ESubscriptionType.valueOf(doc.getString("subscriptionType"));
            sd.price = EPrice.valueOf(doc.getString("price"));
            sd.expiryDate = doc.getString("expiryDate");
            sd.subscriptionDate = doc.getString("subscriptionDate");
            allSubscriptionDao.add(sd);
        }
        return allSubscriptionDao;
    }

    private static boolean oidExist(long oid) {
        SubscriptionDao temp = new SubscriptionDao(oid);
        return temp.getOid() == oid;
    }

    public long getOid() {
        return this.oid;
    }

    public ESubscriptionType getSubscriptionType() {
        return this.subscriptionType;
    }

    public EPrice getPrice() {
        return this.price;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getSubscriptionDate() {
        return this.subscriptionDate;
    }
}
