package com.tinkoff.translator.mappers;

import com.tinkoff.translator.entities.RequestEntity;

import java.time.OffsetDateTime;

public class RequestMapper {
    public RequestEntity toRequest(OffsetDateTime requestTime, String clientIp) {
        return RequestEntity.builder().requestTime(requestTime).clientIp(clientIp).build();
    }
}
