package com.tinkoff.translator.client.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class YaMessageDto {

    @JsonSetter("sourceLanguageCode")
    private String sourceLanguage;
    @JsonSetter("targetLanguageCode")
    private String targetLanguage;
    private String folderId;
    private List<String> texts;
}
