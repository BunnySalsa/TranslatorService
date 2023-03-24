package com.tinkoff.translator.mappers;

import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.dto.MessageDto;

import java.util.List;

public class MessageDtoMapper {
    private static final String PATTERN = "\\P{L}+";

    public YaMessageDto toYaDto(MessageDto messageDto) {
        return YaMessageDto.builder().sourceLanguage(messageDto.getSourceLanguage())
                .targetLanguage(messageDto.getTargetLanguage())
                .texts(List.of(messageDto.getMessage().split(PATTERN))).build();
    }
}
