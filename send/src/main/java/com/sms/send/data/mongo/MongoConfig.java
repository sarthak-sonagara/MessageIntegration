package com.sms.send.data.mongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {
    protected static final String matchedCollectionName = "MatchedCOL";
    protected static final String unmatchedCollectionName = "UnmatchedCOL";
    protected static final String regexCollectionName = "RegexList";
    @Bean
    protected MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
    @Bean
    protected MongoOperations mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "UniversalDB");
    }
}
