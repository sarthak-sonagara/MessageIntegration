package com.sms.store.data;

import com.sms.store.kafka.KafkaService;
import com.sms.universal.UniversalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataService {
    private final KafkaService kafkaService;

    @Autowired
    public DataService(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    public void storeInDatabases(){
        kafkaService.getUniversalMessage();
        // save in multiple databases
    }
}
