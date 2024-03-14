package com.sms.store.elastic;

import com.sms.universal.UniversalMessage;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "universalind")
public class ElasticUniversalMessage extends UniversalMessage {
    public ElasticUniversalMessage(UniversalMessage universalMessage){
        super(universalMessage);
    }
}
