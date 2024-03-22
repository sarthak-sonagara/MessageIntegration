package com.sms.send.mongo;

import com.sms.send.entities.Regex;
import com.sms.universal.UniversalMessage;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MongoService {
    private final MongoOperations mongoTemplate;

    public MongoService(MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void storeUniversalMessage(List<UniversalMessage> messages){
        mongoTemplate.insert(messages,"UniversalCOL");
    }

    public List<UniversalMessage> getUniversalMessages(){
        return mongoTemplate.findAll(UniversalMessage.class,"UniversalCOL");
    }

    public void storeRegexList(List<String> regexStringList) {
        List<Regex> regexList = regexStringList.stream().map(Regex::new).collect(Collectors.toList());
        mongoTemplate.insert(regexList,"RegexList");
    }

    public List<String> getRegexList(){
        List<Regex> regexList = mongoTemplate.findAll(Regex.class,"RegexList");
        return regexList.stream().map(Regex::getRegex).collect(Collectors.toList());
    }
}
