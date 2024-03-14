package com.sms.send.kafka;

import com.sms.universal.UniversalMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
@Configuration
@ComponentScan(basePackages = "com.sms.send")
public class KafkaConfig {
    public final static String topicName = "StoreInDatabase";
    @Bean
    public Producer<String, UniversalMessage>  getProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers","localhost:9092");
        properties.put("zookeeper.connect","localhost:2181");
        properties.put("linger.ms",10000);
        properties.put("key.serializer",StringSerializer.class.getName());
        properties.put("value.serializer",UniversalMessageSerializer.class.getName());
        return new KafkaProducer<String, UniversalMessage>(properties);
    }
}
