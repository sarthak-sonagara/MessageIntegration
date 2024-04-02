package com.sms.send.data;

import com.sms.send.data.elastic.ElasticService;
import com.sms.send.data.entities.ProcessedMessages;
import com.sms.send.data.mongo.MongoService;
import com.sms.send.regex.ProcessingService;
import com.sms.send.data.entities.UniversalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private final ProcessingService processingService;
    private final MongoService mongoService;
    private final ElasticService elasticService;

    @Autowired
    public DataService(ProcessingService processingService, MongoService mongoService, ElasticService elasticService) {
        this.processingService = processingService;
        this.mongoService = mongoService;
        this.elasticService = elasticService;
    }
    private List<String> getRegexList(){
        return mongoService.getRegexList();
    }

    public void storeRegexList(List<String> regexList){
        mongoService.storeRegexList(regexList);
    }

    public void storeIncomingMessages(){
        while(true){
            ProcessedMessages messages = processingService.getProcessedMessages(getRegexList());
            mongoService.storeMatchedMessages(messages.getMatchedMessages());
            mongoService.storeUnmatchedMessages(messages.getUnmatchedMessages());
            elasticService.storeUniversalMessage(messages.getMatchedMessages());
        }
    }

    public List<UniversalMessage> getFreeTextSearchResult(String input){
        return elasticService.freeTextSearch(input);
    }

}
