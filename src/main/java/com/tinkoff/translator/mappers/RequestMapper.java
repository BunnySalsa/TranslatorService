package com.tinkoff.translator.mappers;

import com.tinkoff.translator.db.entities.RequestEntity;

public class RequestMapper {
     public RequestEntity toQuery(int answerTime, String clientIp) {
         return RequestEntity.builder().answerTime(answerTime).clientIp(clientIp).build();
     }
}
