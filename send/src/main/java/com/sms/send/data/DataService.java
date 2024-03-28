package com.sms.send.data;

import com.sms.send.data.elastic.ElasticService;
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
            List<UniversalMessage> messages = processingService.getProcessedMessages(getRegexList());
            mongoService.storeUniversalMessage(messages);
            elasticService.storeUniversalMessage(messages);
        }
    }

}
