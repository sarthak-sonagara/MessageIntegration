package com.sms.send.channels.reddit;

public class StatusCodeException extends Exception{
    public StatusCodeException(Integer code) {
        this.code = code;
    }

    private final Integer code;
    @Override
    public String getMessage() {
        return "Error code : " + code;
    }
}
