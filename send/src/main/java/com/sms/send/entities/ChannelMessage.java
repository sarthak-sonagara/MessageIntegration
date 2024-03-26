package com.sms.send.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sms.universal.UniversalMessage;

public interface ChannelMessage {
    UniversalMessage convertToUniversalMessage();
    String getChannelName();
}
