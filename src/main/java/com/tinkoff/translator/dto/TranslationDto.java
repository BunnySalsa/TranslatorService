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
public class TranslationDto {

    private String sourceLanguage;
    private String targetLanguage;
    private List<String> translatedWords;
}
