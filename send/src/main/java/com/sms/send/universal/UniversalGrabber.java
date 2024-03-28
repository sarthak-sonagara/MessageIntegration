package com.sms.send.universal;

import com.sms.send.kafka.KafkaService;
import com.sms.send.data.entities.UniversalMessage;
import org.springframework.stereotype.Service;

@Service
public abstract class UniversalGrabber {
    protected final KafkaService kafkaService;

    protected UniversalGrabber(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    public void convertAndPutMessage(String rawMessage){
        UniversalMessage universalMessage = convertToUniversalMessage(rawMessage);
        kafkaService.putUniversalMessage(universalMessage);
    }

    protected abstract UniversalMessage convertToUniversalMessage(String rawMessage);

}
