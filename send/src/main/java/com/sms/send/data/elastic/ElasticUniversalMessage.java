package com.sms.send.data.elastic;

import com.sms.send.data.entities.UniversalMessage;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "universalind")
public class ElasticUniversalMessage extends UniversalMessage {
    public ElasticUniversalMessage(){}
    public ElasticUniversalMessage(UniversalMessage universalMessage){
        super(universalMessage);
    }
}
