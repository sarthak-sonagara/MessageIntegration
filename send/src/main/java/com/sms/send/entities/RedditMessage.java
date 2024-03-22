package com.sms.send.entities;

import com.sms.universal.UniversalMessage;

public class RedditMessage implements ChannelMessage{
    private final String redditMessage;
    public RedditMessage(String redditMessage) {
        this.redditMessage = redditMessage;
    }
    @Override
    public UniversalMessage convertToUniversalMessage() {
        return new UniversalMessage(redditMessage,getChannelName());
    }

    @Override
    public String getChannelName() {
        return "REDDIT";
    }
}
