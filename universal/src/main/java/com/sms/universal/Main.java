package com.sms.universal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] strings){
        ApplicationContext app = new AnnotationConfigApplicationContext();
        app.getApplicationName();
    }
}
