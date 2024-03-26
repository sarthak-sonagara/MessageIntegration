package com.sms.send;
import com.sms.send.data.DataService;
import com.sms.send.kafka.KafkaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaConfig.class);
        DataService dataService = ctx.getBean(DataService.class);
        dataService.storeIncomingMessages();
    }
}