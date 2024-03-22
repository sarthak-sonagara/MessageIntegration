package com.sms.send.data;

import com.sms.send.elastic.ElasticService;
import com.sms.send.kafka.KafkaService;
import com.sms.send.mongo.MongoService;
import com.sms.universal.UniversalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private final KafkaService kafkaService;
    private final MongoService mongoService;
    private final ElasticService elasticService;

    @Autowired
    public DataService(KafkaService kafkaService, MongoService mongoService, ElasticService elasticService) {
        this.kafkaService = kafkaService;
        this.mongoService = mongoService;
        this.elasticService = elasticService;
    }

    public void storeInDatabases(){
        List<UniversalMessage> messages = kafkaService.getUniversalMessage();
        System.out.println("before mongo");
        mongoService.storeUniversalMessage(messages);
        System.out.println("before elastic");
        elasticService.storeUniversalMessage(messages);
    }
}
