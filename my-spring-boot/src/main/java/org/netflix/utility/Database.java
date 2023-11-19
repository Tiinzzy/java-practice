package org.netflix.utility;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


public class Database {
    public static Database INSTANCE = new Database();
    private MongoClient mongoClient = null;

    private static final String HOST = "localhost";
    private static final int PORT = 27017;

    private static final String DATABASE_NAME = "netflix";

    private Database() {
        try {
            mongoClient = new MongoClient(HOST, PORT);
            mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public MongoDatabase getNetflixDatabase() {
        return this.mongoClient.getDatabase(DATABASE_NAME);
    }

}
