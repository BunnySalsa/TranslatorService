package com.tinkoff.translator.db.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {
    private Long id;
    private int answerTime;
    private String clientIp;
}
