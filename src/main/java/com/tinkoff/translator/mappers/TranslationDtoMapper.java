package com.tinkoff.translator.mappers;

import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;

public class TranslationDtoMapper {

    public static final String DIDN_T_DETECTED = "didn't detected";

    public TranslationDto toServiceDto(YaTranslationDto translationDto, MessageDto messageDto) {
        String detectedLanguage = translationDto.getTranslations().isEmpty() ? DIDN_T_DETECTED
                : translationDto.getTranslations().get(0).getDetectedLanguageCode();
        return TranslationDto.builder()
                .sourceLanguage(messageDto.getSourceLanguage() == null ? detectedLanguage : messageDto.getSourceLanguage())
                .targetLanguage(messageDto.getTargetLanguage())
                .translatedWords(translationDto.getTranslations().stream().map(TextDto::getText).toList()).build();
    }
}
