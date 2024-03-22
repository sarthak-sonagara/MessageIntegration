package com.sms.store;

import com.sms.store.data.DataService;

import java.util.Scanner;

public class Run {
    public static void start(DataService dataService){
        while(true){
            dataService.storeInDatabases();
        }
    }
}
