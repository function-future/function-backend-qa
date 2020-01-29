package com.future.function.qa.util;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Cleaner {

  private final MongoDatabase mongoDatabase;

  private Map<String, List<String>> sourcesAndIds = new ConcurrentHashMap<>();

  @Autowired
  public Cleaner(MongoDatabase mongoDatabase) {

    this.mongoDatabase = mongoDatabase;
  }

  public void append(String source, String id) {

    if (!sourcesAndIds.containsKey(source)) {
      sourcesAndIds.put(source, new CopyOnWriteArrayList<>());
    }

    sourcesAndIds.get(source)
      .add(id);
  }

  public void flushAll() {

    sourcesAndIds.forEach((collectionName, ids) -> ids.forEach(id -> {
      if (!collectionName.equals(DocumentName.FILE)) {
        this.hardDeleteFromDatabase(collectionName, id);
      } else {
        this.setMarkUsedFalse(collectionName, id);
      }
      sourcesAndIds.get(collectionName)
        .remove(id);
    }));
  }

  private void hardDeleteFromDatabase(String collectionName, String id) {

    mongoDatabase.getCollection(collectionName)
      .deleteOne(
        new BasicDBObject("_id", ObjectId.isValid(id) ? new ObjectId(id) : id));
  }

  private void setMarkUsedFalse(String collectionName, String id) {

    mongoDatabase.getCollection(collectionName)
      .updateOne(new BasicDBObject("_id", id), Updates.set("used", false));
  }

}
