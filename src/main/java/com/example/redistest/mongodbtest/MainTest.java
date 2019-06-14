package com.example.redistest.mongodbtest;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

    public static void main(String[] args){
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = mongoDatabase.getCollection("users");
        Document document = new Document("name", "haixiao")
                .append("sex", "male").append("age", 27);
        collection.insertOne(document);
        List<Document> list = new ArrayList<>();
        for(int i = 0; i <=30; i++){
            Document d = new Document("name", "haixiao" + i)
                    .append("sex", "male").append("age", i);
            list.add(d);
        }
        collection.insertMany(list);
        Bson filter = Filters.eq("age", 18);
        collection.deleteOne(filter);
//        FindIterable findIterable = collection.find();
//        MongoCursor cursor = findIterable.iterator();
//        while (cursor.hasNext()){
//            System.out.println(cursor.next());
//        }
    }

}
