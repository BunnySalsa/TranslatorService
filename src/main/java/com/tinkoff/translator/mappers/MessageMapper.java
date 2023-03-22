package com.tinkoff.translator.mappers;

import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.dto.MessageDto;

import java.util.List;

public class MessageMapper {
    private static final String PATTERN = "\\P{L}+";

    public YaMessageDto toYaDto(MessageDto messageDto) {
        return YaMessageDto.builder().sourceLang(messageDto.getSourceLang())
                .targetLang(messageDto.getTargetLang())
                .texts(List.of(messageDto.getMessage().split(PATTERN))).build();
    }
}
