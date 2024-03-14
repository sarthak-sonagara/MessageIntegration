package com.sms.send.entities;

import com.sms.universal.UniversalMessage;

public interface ChannelMessage {
    UniversalMessage convertToUniversalMessage();
    String getChannelName();
}
