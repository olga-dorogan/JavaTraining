package com.custom;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

/**
 * Created by olga on 06.05.15.
 */
public class MainClass {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("myDB");
        MongoIterable<String> listDatabaseNames = mongoClient.listDatabaseNames();
        for (String dbName : listDatabaseNames) {
            System.out.println(dbName);
        }
        MongoCollection<Document> dbCollection = db.getCollection("heroes");
        /*======== INSERT ==========*/
        fillMongoCollection(dbCollection);

        /*========= READ ===========*/
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("role", "main");
        FindIterable<Document> findIterable = dbCollection.find(searchQuery);
        for (Document doc : findIterable) {
            System.out.println(doc);
        }

        /*========= UPDATE ===========*/
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set", new BasicDBObject("name", "Amy Cooper"));
        dbCollection.updateOne(new BasicDBObject("name", "Amy"), updateQuery);

        /*========= DELETE ==========*/
        BasicDBObject deleteQuery = new BasicDBObject("name", "Leonard");
        dbCollection.deleteOne(deleteQuery);

        /*========= READ ===========*/
        System.out.println("========= After update() and delete()==========");
         searchQuery = new BasicDBObject();
        searchQuery.put("role", "main");
        findIterable = dbCollection.find(searchQuery);
        for (Document doc : findIterable) {
            System.out.println(doc);
        }

        /*====== DELETE ALL ========*/
        dbCollection.deleteMany(new BasicDBObject());
        // or delete an drop all collection
//        dbCollection.drop();

        mongoClient.close();
    }

    private static void fillMongoCollection(MongoCollection<Document> mongoCollection) {
        Document document = new Document();
        document.put("name", "Leonard");
        document.put("actor", "Johnny Galecki");
        document.put("age", 40);
        document.put("role", "main");
        mongoCollection.insertOne(document);

        document = new Document();
        document.put("name", "Sheldon");
        document.put("actor", "Jim Parsons");
        document.put("age", 42);
        document.put("role", "main");
        mongoCollection.insertOne(document);

        document = new Document();
        document.put("name", "Amy");
        document.put("actor", "Mayim Bialik");
        document.put("age", 39);
        document.put("role", "main");
        mongoCollection.insertOne(document);

        document = new Document();
        document.put("name", "Leslie");
        document.put("actor", "Sara Gilbert");
        document.put("age", 40);
        document.put("role", "secondary");
        mongoCollection.insertOne(document);

    }
}
