package com.sms.send.kafka;

import com.sms.universal.UniversalMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class KafkaService {
    private final Producer<String, UniversalMessage> producer;
    private final Consumer<String, UniversalMessage> consumer;
    @Autowired
    public KafkaService(Producer<String, UniversalMessage> producer, Consumer<String, UniversalMessage> consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }
    public void putUniversalMessage(UniversalMessage message){
        producer.send(new ProducerRecord<>(KafkaConfig.topicName, message.getSource(),message));
    }
    public List<UniversalMessage> getUniversalMessages(){
//        System.out.println("Before poll");
        ConsumerRecords<String,UniversalMessage> records = consumer.poll(Duration.ofMillis(100000));
//        System.out.println("After poll");
        List<UniversalMessage> messages = new ArrayList<>();
        for(ConsumerRecord<String,UniversalMessage> record : records){
            messages.add(record.value());
            System.out.println(record.value().getContent());
        }
        return messages;
    }
}
