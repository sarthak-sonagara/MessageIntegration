package com.sms.send.elastic;

import com.sms.universal.UniversalMessage;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticService {
    private final ElasticsearchOperations elasticsearchOperations;

    public ElasticService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void storeUniversalMessage(List<UniversalMessage> messages){
        elasticsearchOperations.save(convertToElasticUniversalMessages(messages));
    }

    private List<ElasticUniversalMessage> convertToElasticUniversalMessages(List<UniversalMessage> universalMessages){
        List<ElasticUniversalMessage> elasticUniversalMessages = new ArrayList<>();
        for(UniversalMessage universalMessage : universalMessages){
            elasticUniversalMessages.add(new ElasticUniversalMessage(universalMessage));
        }
        return elasticUniversalMessages;
    }
}
