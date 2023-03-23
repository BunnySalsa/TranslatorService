package com.tinkoff.translator.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class YaTranslationDto {

    private List<TextDto> translations;

    public YaTranslationDto() {
        translations = new ArrayList<>();
    }
}
