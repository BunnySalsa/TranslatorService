package com.tinkoff.translator.services;

import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;
import com.tinkoff.translator.mappers.MessageDtoMapper;
import com.tinkoff.translator.mappers.TranslationDtoMapper;
import com.tinkoff.translator.mappers.TranslationMapper;
import com.tinkoff.translator.repositories.RequestRepository;
import com.tinkoff.translator.repositories.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class TranslationService {


    private final TranslatorClient<YaMessageDto, YaTranslationDto> client;
    private final TranslationRepository translationRepository;
    private final RequestRepository requestRepository;
    private final TranslationMapper translationMapper;
    private final MessageDtoMapper messageDtoMapper;
    private final TranslationDtoMapper translationDtoMapper;
    private final StorageService storageService;

    public TranslationDto translate(MessageDto messageDto, String clientIp) throws ExecutionException, InterruptedException {
        YaMessageDto yaMessageDto = messageDtoMapper.toYaDto(messageDto);
        YaTranslationDto yaTranslationDto = client.translate(yaMessageDto);
        storageService.save(clientIp, yaMessageDto, yaTranslationDto);
        return translationDtoMapper.toServiceDto(yaTranslationDto, messageDto);
    }
}
