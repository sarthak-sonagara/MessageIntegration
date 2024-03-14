package com.sms.store;
import com.sms.store.data.DataService;
import com.sms.store.kafka.KafkaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaConfig.class);
        DataService dataService = ctx.getBean(DataService.class);
        Run.start(dataService);
    }
}