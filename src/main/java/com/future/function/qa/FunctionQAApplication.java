package com.future.function.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.function.qa.properties.FunctionProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableConfigurationProperties(value = FunctionProperties.class)
@ComponentScan(basePackages = {
  "com.future.function.qa", "net.thucydides", "net.serenitybdd"
})
public class FunctionQAApplication {

  @Bean
  public ObjectMapper mapper() {

    return new ObjectMapper();
  }

  @Bean
  public MongoDatabase mongoDatabase(FunctionProperties functionProperties) {

    String uri = String.valueOf(functionProperties.getDatabase()
                                  .get("uri"));
    String databaseName = String.valueOf(functionProperties.getDatabase()
                                           .get("name"));

    return new MongoClient(new MongoClientURI(uri)).getDatabase(databaseName);
  }

}
