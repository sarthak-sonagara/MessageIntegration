package com.sms.send;

import com.sms.send.controller.Controller;

import java.util.Scanner;

public class Run {
    public static void start(Controller controller){
        while(true){
            Scanner myObj = new Scanner(System.in);
            String teamsMessage = myObj.nextLine();
            if(teamsMessage.equals("EXIT"))
                break;
            //controller.putMessageFromTeams(teamsMessage);
        }
    }
}
