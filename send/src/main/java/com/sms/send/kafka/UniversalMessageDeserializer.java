package com.sms.send.kafka;

import com.sms.universal.UniversalMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class UniversalMessageDeserializer implements Deserializer<UniversalMessage> {

    @Override
    public UniversalMessage deserialize(String s, byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return (UniversalMessage) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("UniversalMessage Deserialization Error",e);
        }
    }
}
