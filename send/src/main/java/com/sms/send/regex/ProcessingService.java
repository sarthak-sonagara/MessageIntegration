package com.sms.send.regex;

import com.sms.send.data.entities.ProcessedMessages;
import com.sms.send.kafka.KafkaService;
import com.sms.send.data.entities.UniversalMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProcessingService {
    private final KafkaService kafkaService;

    public ProcessingService(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    public ProcessedMessages getProcessedMessages(List<String> regexList){
        List<UniversalMessage> allMessages = kafkaService.getUniversalMessages();
        List<UniversalMessage> matchedMessages = new ArrayList<>();
        List<UniversalMessage> unmatchedMessages = new ArrayList<>();
        for(UniversalMessage message: allMessages){
            if(regexList.stream()
                    .anyMatch(regex -> Pattern.compile(regex)
                            .matcher(message.getContent())
                            .matches())
            ){
                matchedMessages.add(message);
            }
            else{
                unmatchedMessages.add(message);
            }
        }
        return new ProcessedMessages(matchedMessages,unmatchedMessages);
    }
}
