package com.tinkoff.translator.services;

import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.db.entities.RequestEntity;
import com.tinkoff.translator.db.entities.TranslationResult;
import com.tinkoff.translator.db.repositories.QueryRepository;
import com.tinkoff.translator.db.repositories.TranslationRepository;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;
import com.tinkoff.translator.mappers.MessageDtoMapper;
import com.tinkoff.translator.mappers.RequestMapper;
import com.tinkoff.translator.mappers.TranslationDtoMapper;
import com.tinkoff.translator.mappers.TranslationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.time.LocalTime;
import java.util.List;

@Service
public class TranslationService {

    private TranslatorClient<YaMessageDto, YaTranslationDto> client;
    private TranslationRepository translationRepository;
    private QueryRepository queryRepository;
    private TranslationMapper translationMapper;
    private MessageDtoMapper messageDtoMapper;
    private TranslationDtoMapper translationDtoMapper;
    private RequestMapper requestMapper;

    public TranslationService(@Autowired TranslatorClient<YaMessageDto, YaTranslationDto> client,
                              @Autowired TranslationMapper translationMapper,
                              @Autowired MessageDtoMapper messageDtoMapper,
                              @Autowired RequestMapper requestMapper,
                              @Autowired TranslationDtoMapper translationDtoMapper,
                              @Autowired QueryRepository queryRepository,
                              @Autowired TranslationRepository translationRepository) {
        this.client = client;
        this.translationRepository = translationRepository;
        this.queryRepository = queryRepository;
        this.translationMapper = translationMapper;
        this.messageDtoMapper = messageDtoMapper;
        this.translationDtoMapper = translationDtoMapper;
        this.requestMapper = requestMapper;
    }

    public TranslationDto serve(MessageDto messageDto, int divider, int start, String clientIp) {
        YaMessageDto yaMessageDto = messageDtoMapper.toYaDto(messageDto);
        YaTranslationDto yaTranslationDto = client.translate(yaMessageDto);
        RequestEntity request = requestMapper.toQuery(Math.abs(LocalTime.now().getNano() - start) / divider, clientIp);
        queryRepository.save(request);
        List<TranslationResult> resultList = translationMapper
                .toTranslationResult(request.getId(), yaTranslationDto, yaMessageDto);
        for (TranslationResult result : resultList) {
            translationRepository.save(result);
        }
        return translationDtoMapper.toServiceDto(yaTranslationDto, messageDto);
    }
}
