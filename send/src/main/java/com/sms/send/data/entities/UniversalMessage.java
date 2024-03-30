package com.sms.send.data.entities;


import java.io.Serializable;

public class UniversalMessage implements Serializable {
    private String content;
    private String source;

    public UniversalMessage(){}
    public UniversalMessage(String content, String source) {
        this.content = content;
        this.source = source;
    }

    public UniversalMessage(UniversalMessage universalMessage){
        this.content = universalMessage.getContent();
        this.source = universalMessage.getSource();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
