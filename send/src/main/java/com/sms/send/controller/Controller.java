package com.sms.send.controller;
import com.sms.send.channels.ChannelService;
import com.sms.send.entities.TeamsMessage;
import org.springframework.beans.factory.annotation.Autowired;
@org.springframework.stereotype.Controller
public class Controller {
    private final ChannelService channelService;

    @Autowired
    public Controller(ChannelService channelService) {
        this.channelService = channelService;
    }

    public void putMessageFromTeams(String teamsMessage){
        channelService.putMessage(new TeamsMessage(teamsMessage));
    }
}
