package com.tinkoff.translator.services;

import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;
import com.tinkoff.translator.mappers.MessageDtoMapper;
import com.tinkoff.translator.mappers.TranslationDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final TranslatorClient<YaMessageDto, YaTranslationDto> client;
    private final StorageService storageService;

    public TranslationDto translate(MessageDto messageDto, String clientIp) throws InterruptedException, HttpClientErrorException {
        YaMessageDto yaMessageDto = new MessageDtoMapper().toYaDto(messageDto);
        YaTranslationDto yaTranslationDto = client.translate(yaMessageDto);
        storageService.save(clientIp, yaMessageDto, yaTranslationDto);
        return new TranslationDtoMapper().toServiceDto(yaTranslationDto, messageDto);
    }
}
