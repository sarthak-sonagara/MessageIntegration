package com.sms.send.entities;

import com.sms.universal.UniversalMessage;

public class TeamsMessage implements ChannelMessage{
    private final String teamsMessage;

    public TeamsMessage(String teamsMessage) {
        this.teamsMessage = teamsMessage;
    }

    @Override
    public UniversalMessage convertToUniversalMessage() {
        return new UniversalMessage(teamsMessage,getChannelName());
    }

    @Override
    public String getChannelName() {
        return "TEAMS";
    }
}
