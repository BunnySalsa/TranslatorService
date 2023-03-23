package com.tinkoff.translator.mappers;

import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;

public class TranslationMapper {

    public TranslationDto toServiceDto(YaTranslationDto translationDto, MessageDto messageDto) {
        return TranslationDto.builder()
                .sourceLang(messageDto.getSourceLang() == null ?
                        translationDto.getTranslations().get(0).getDetectedLanguageCode() : messageDto.getSourceLang())
                .targetLang(messageDto.getTargetLang())
                .translatedWords(translationDto.getTranslations().stream().map(TextDto::getText).toList()).build();
    }
}
