package org.netflix.model.customer;

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
        this.oid = oid;
        var db = Database.INSTANCE.getNetflixDatabase();
        Document doc = db.getCollection(CUSTOMER_COLLECTION).find(eq("oid", oid)).first();
        if (doc != null) {
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
        db.getCollection(CUSTOMER_COLLECTION).insertOne(document);
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
}
