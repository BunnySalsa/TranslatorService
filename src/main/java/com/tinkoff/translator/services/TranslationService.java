package com.tinkoff.translator.services;

import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    private TranslatorClient<YaMessageDto, YaTranslationDto> client;

    public TranslationService(@Autowired TranslatorClient<YaMessageDto, YaTranslationDto> client) {
        this.client = client;
    }

    public TranslationDto serve(MessageDto message) {
        YaMessageDto yaMessageDto = YaMessageDto.builder()
                .sourceLang(message.getSourceLang())
                .targetLang(message.getTargetLang())
                .texts(List.of(message.getMessage().split(" "))).build();
        YaTranslationDto result = client.translate(yaMessageDto);
        return TranslationDto.builder()
                .sourceLang(message.getSourceLang())
                .targetLang(message.getTargetLang())
                .translatedWords(result.getTranslations().stream().map(TextDto::getText).collect(Collectors.toList()))
                .build();
    }
}
