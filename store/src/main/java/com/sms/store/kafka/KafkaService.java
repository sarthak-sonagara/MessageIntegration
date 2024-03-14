package com.sms.store.kafka;

import com.sms.store.elastic.ElasticService;
import com.sms.store.mongo.MongoService;
import com.sms.universal.UniversalMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KafkaService {
    private final Consumer<String, UniversalMessage> consumer;
    private final MongoService mongoService;
    private final ElasticService elasticService;
    @Autowired
    public KafkaService(Consumer<String, UniversalMessage> consumer, MongoService mongoService, ElasticService elasticService) {
        this.consumer = consumer;
        this.mongoService = mongoService;
        this.elasticService = elasticService;
    }
    public void getUniversalMessage(){
        System.out.println("Before poll");
        ConsumerRecords<String,UniversalMessage> records = consumer.poll(Duration.ofMillis(100000));
        System.out.println("After poll");
        List<UniversalMessage> messages = new ArrayList<>();
        for(ConsumerRecord<String,UniversalMessage> record : records){
            messages.add(record.value());
            System.out.println(record.value().getContent());
        }
        System.out.println("before mongo");
        mongoService.storeUniversalMessage(messages);
        System.out.println("before elastic");
        elasticService.storeUniversalMessage(messages);
    }
}
