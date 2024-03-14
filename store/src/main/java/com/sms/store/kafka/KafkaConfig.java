package com.sms.store.kafka;

import com.sms.universal.UniversalMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;
@Configuration
@ComponentScan(basePackages = "com.sms.store")
public class KafkaConfig {
    public final static String topicName = "StoreInDatabase";

    @Bean
    public Consumer<String, UniversalMessage> getConsumer(){
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
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
