package com.sms.send.data.entities;

import java.util.ArrayList;
import java.util.List;

public class ProcessedMessages {
    private final List<UniversalMessage> matchedMessages;
    private final List<UniversalMessage> unmatchedMessages;

    public ProcessedMessages(List<UniversalMessage> matchedMessages, List<UniversalMessage> unmatchedMessages) {
        this.matchedMessages = matchedMessages;
        this.unmatchedMessages = unmatchedMessages;
    }

    public List<UniversalMessage> getMatchedMessages() {
        return matchedMessages;
    }

    public List<UniversalMessage> getUnmatchedMessages() {
        return unmatchedMessages;
    }
}
