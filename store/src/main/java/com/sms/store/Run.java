package com.sms.store;

import com.sms.store.data.DataService;

import java.util.Scanner;

public class Run {
    public static void start(DataService dataService){
        while(true){
            Scanner myObj = new Scanner(System.in);
            String input = myObj.nextLine();
            if(input.equals("EXIT"))
                break;
            dataService.storeInDatabases();
        }
    }
}
