package com.sms.send.regex;

import com.sms.send.kafka.KafkaService;
import com.sms.send.data.entities.UniversalMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProcessingService {
    private final KafkaService kafkaService;

    public ProcessingService(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    public List<UniversalMessage> getProcessedMessages(List<String> regexList){
        List<UniversalMessage> allMessages = kafkaService.getUniversalMessages();

        return allMessages.stream()
                .filter(message ->
                        regexList.stream().anyMatch(regex ->
                                Pattern.compile(regex).matcher(message.getContent()).matches()))
                .collect(Collectors.toList());
    }
}
