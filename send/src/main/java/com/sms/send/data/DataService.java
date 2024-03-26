package com.sms.send.data;

import com.sms.send.elastic.ElasticService;
import com.sms.send.mongo.MongoService;
import com.sms.send.regex.ProcessingService;
import com.sms.universal.UniversalMessage;
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

    // method to store regex in db

    // method to get regex from db
    private List<String> getRegexList(){
        return mongoService.getRegexList();
    }

    public void storeRegexList(List<String> regexList){
        mongoService.storeRegexList(regexList);
    }

    public void storeIncomingMessages(){
        while(true){
            List<UniversalMessage> messages = processingService.getProcessedMessages(getRegexList());
//            System.out.println("processed messages:");
//            System.out.println(messages);
//            System.out.println("before mongo");
            mongoService.storeUniversalMessage(messages);
//            System.out.println("before elastic");
            elasticService.storeUniversalMessage(messages);
        }
    }

}
