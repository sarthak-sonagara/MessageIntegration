package com.sms.send.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.universal.UniversalMessage;

import java.util.Map;

public class RedditMessage implements ChannelMessage{
    private final String redditMessage;
    public RedditMessage(String redditMessage) {
        this.redditMessage = redditMessage;
    }
    @Override
    public UniversalMessage convertToUniversalMessage(){
        return new UniversalMessage(redditMessage,getChannelName());
    }

    @Override
    public String getChannelName() {
        return "REDDIT";
    }
}
