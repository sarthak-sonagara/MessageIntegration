package com.sms.send.channels.reddit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.send.universal.UniversalGrabber;
import com.sms.send.kafka.KafkaService;
import com.sms.send.data.entities.UniversalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedditGrabber extends UniversalGrabber {

    @Autowired
    protected RedditGrabber(KafkaService kafkaService) {
        super(kafkaService);
    }

    @Override
    protected UniversalMessage convertToUniversalMessage(String rawMessage) {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder message = new StringBuilder();
        try {
            JsonNode node = mapper.readTree(rawMessage);
            JsonNode children = node.get("data").get("children");
            Integer messageNumber = 1;
            for(JsonNode child : children){
                message.append(messageNumber).append("----------------------------------\n");
                message.append(child.get("data").get("selftext").asText()).append("\n");
                messageNumber++;
            }
        } catch (JsonProcessingException e) {
            System.out.println("Not storing message because cannot find selftext field.");
        }

        return new UniversalMessage(message.toString(),"REDDIT");
    }
}
