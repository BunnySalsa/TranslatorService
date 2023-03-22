package com.tinkoff.translator.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class YaTranslationDto {

    private List<TextDto> translations;
}
