package com.tinkoff.translator.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {
    private Long id;
    private OffsetDateTime requestTime;
    private String clientIp;
}
