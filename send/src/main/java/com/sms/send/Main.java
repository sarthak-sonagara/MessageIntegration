package com.sms.send;
import com.sms.send.controller.Controller;
import com.sms.send.kafka.KafkaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaConfig.class);
        Controller controller = ctx.getBean(Controller.class);
        Run.start(controller);
    }
}