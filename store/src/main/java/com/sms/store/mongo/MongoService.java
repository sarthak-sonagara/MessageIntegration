package com.sms.store.mongo;

import com.sms.universal.UniversalMessage;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoService {
    private final MongoOperations mongoTemplate;

    public MongoService(MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void storeUniversalMessage(List<UniversalMessage> messages){
        mongoTemplate.insert(messages,"UniversalCOL");
    }
}
