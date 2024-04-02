package com.sms.send.data.mongo;

import com.sms.send.data.entities.Regex;
import com.sms.send.data.entities.UniversalMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoService {
    private final MongoOperations mongoTemplate;

    public MongoService(MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void storeRegexList(@NotNull List<String> regexStringList) {
        List<Regex> regexList = regexStringList.stream().map(Regex::new).collect(Collectors.toList());
        mongoTemplate.remove(new Query(),MongoConfig.regexCollectionName);
        mongoTemplate.insert(regexList,MongoConfig.regexCollectionName);
    }

    public List<String> getRegexList(){
        List<Regex> regexList = mongoTemplate.findAll(Regex.class,MongoConfig.regexCollectionName);
        return regexList.stream().map(Regex::getRegex).collect(Collectors.toList());
    }

    public void storeMatchedMessages(List<UniversalMessage> matchedMessages) {
        mongoTemplate.insert(matchedMessages,MongoConfig.matchedCollectionName);
    }
    public void storeUnmatchedMessages(List<UniversalMessage> unmatchedMessages) {
        mongoTemplate.insert(unmatchedMessages,MongoConfig.unmatchedCollectionName);
    }
}
