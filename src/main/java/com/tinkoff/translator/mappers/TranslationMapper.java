package com.tinkoff.translator.mappers;

import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;

public class TranslationMapper {

    public TranslationDto toServiceDto(YaTranslationDto dto, MessageDto messageDto) {
        return TranslationDto.builder()
                .sourceLang(messageDto.getSourceLang())
                .targetLang(messageDto.getTargetLang())
                .translatedWords(dto.getTranslations().stream().map(TextDto::getText).toList()).build();
    }
}
