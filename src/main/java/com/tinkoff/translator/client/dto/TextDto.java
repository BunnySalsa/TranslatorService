package com.tinkoff.translator.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextDto {
    private String text;
    private String detectedLanguageCode;

    public TextDto(String text) {
        this.text = text;
    }
}
