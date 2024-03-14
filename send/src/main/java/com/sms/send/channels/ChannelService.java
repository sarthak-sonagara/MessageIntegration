package com.sms.send.channels;

import com.sms.send.kafka.KafkaService;
import com.sms.send.entities.ChannelMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {
    private final KafkaService kafkaService;
    @Autowired
    public ChannelService(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }
    public void putMessage(ChannelMessage message){
        kafkaService.putUniversalMessage(message.convertToUniversalMessage());
    }

}
