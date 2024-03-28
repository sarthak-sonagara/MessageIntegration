package com.sms.send.kafka;

import com.sms.send.data.entities.UniversalMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;
@Configuration
@ComponentScan(basePackages = "com.sms.send")
public class KafkaConfig {
    protected final static String topicName = "StoreInDatabase";
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

    @Bean
    public Consumer<String, UniversalMessage> getConsumer(){
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.put("zookeeper.connect","localhost:2181");
        properties.setProperty("group.id", "StoreInDatabase");
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", UniversalMessageDeserializer.class.getName());
        KafkaConsumer<String,UniversalMessage> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(KafkaConfig.topicName));
        return consumer;
    }
}
