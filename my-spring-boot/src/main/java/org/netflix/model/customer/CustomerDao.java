package org.netflix.model.customer;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.netflix.utility.Database;
import org.netflix.utility.OidGenerator;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CustomerDao {
    private static final String CUSTOMER_COLLECTION = "customer";
    private long oid = -1;
    private String name = null;
    private String phoneNo = null;
    private String email = null;

    private CustomerDao() {
    }

    public CustomerDao(String name, String phoneNo, String email) {
        this.oid = OidGenerator.getNew();
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public CustomerDao(long oid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(CUSTOMER_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
            this.oid = oid;
            this.name = doc.getString("name");
            this.phoneNo = doc.getString("phoneNo");
            this.email = doc.getString("email");
        }
    }

    public void saveToTable() {
        var db = Database.INSTANCE.getNetflixDatabase();
        Document document = new Document();
        document.append("oid", this.oid);
        document.append("name", this.name);
        document.append("phoneNo", this.phoneNo);
        document.append("email", this.email);
        if (CustomerDao.oidExist(this.oid)) {
            db.getCollection(CUSTOMER_COLLECTION).replaceOne(eq("oid", this.oid), document);
        } else {
            db.getCollection(CUSTOMER_COLLECTION).insertOne(document);
        }
    }

    public static List<CustomerDao> loadAll() {
        var db = Database.INSTANCE.getNetflixDatabase();
        var docs = db.getCollection(CUSTOMER_COLLECTION).find();
        List<CustomerDao> allCustomerDao = new ArrayList<>();
        for (var doc : docs) {
            CustomerDao cd = new CustomerDao();
            cd.oid = doc.getLong("oid");
            cd.name = doc.getString("name");
            cd.phoneNo = doc.getString("phoneNo");
            cd.email = doc.getString("email");
            allCustomerDao.add(cd);
        }
        return allCustomerDao;
    }

    public void delete(long oid) {
        var db = Database.INSTANCE.getNetflixDatabase();
        MongoCollection<Document> collection = db.getCollection(CUSTOMER_COLLECTION);
        try {
            DeleteResult result = collection.deleteOne(eq("oid", oid));
            System.out.println("Deleted document count: " + result.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }
    }

    private static boolean oidExist(long oid) {
        CustomerDao temp = new CustomerDao(oid);
        return temp.getOid() == oid;
    }

    public long getOid() {
        return oid;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
