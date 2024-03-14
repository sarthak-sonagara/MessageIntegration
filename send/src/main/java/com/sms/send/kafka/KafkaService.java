package com.sms.send.kafka;

import com.sms.universal.UniversalMessage;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class KafkaService {
    private final Producer<String, UniversalMessage> producer;
    @Autowired
    public KafkaService(Producer<String, UniversalMessage> producer) {
        this.producer = producer;
    }
    public void putUniversalMessage(UniversalMessage message){
        producer.send(new ProducerRecord<>(KafkaConfig.topicName, message.getSource(),message));
    }
}
