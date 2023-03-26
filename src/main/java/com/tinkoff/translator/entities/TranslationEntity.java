package com.tinkoff.translator.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationEntity {

    private Long id;
    private Long request;
    private String sourceLang;
    private String targetLang;
    private String sourceText;
    private String targetText;
}
