package com.sms.send.data.elastic;

import com.sms.send.data.entities.UniversalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticService {
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
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

    public List<UniversalMessage> freeTextSearch(String input){
        Criteria criteria = new Criteria("content").contains(input);
        Query query = new CriteriaQuery(criteria);
        SearchHits<ElasticUniversalMessage> hits = elasticsearchOperations.search(query,ElasticUniversalMessage.class);
        return hits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
