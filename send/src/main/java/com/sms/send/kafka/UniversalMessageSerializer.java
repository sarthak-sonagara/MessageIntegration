package com.sms.send.kafka;
import com.sms.send.data.entities.UniversalMessage;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class UniversalMessageSerializer implements Serializer<UniversalMessage> {

    @Override
    public byte[] serialize(String s, UniversalMessage universalMessage) {
        try(ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)){
            objectStream.writeObject(universalMessage);
            return byteStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("UniversalMessage Serialization Error",e);
        }    }
}