package com.tinkoff.translator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomingMessageDto {

    private String sourceLang;
    private String targetLang;
    private List<String> texts;
}
