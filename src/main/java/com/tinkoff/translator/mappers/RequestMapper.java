package com.tinkoff.translator.mappers;

import com.tinkoff.translator.entities.RequestEntity;

import java.time.OffsetDateTime;

public class RequestMapper {
    public RequestEntity toRequest(OffsetDateTime answerTime, String clientIp) {
        return RequestEntity.builder().requestTime(answerTime).clientIp(clientIp).build();
    }
}
