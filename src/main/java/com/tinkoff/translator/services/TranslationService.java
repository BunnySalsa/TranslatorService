package com.tinkoff.translator.services;

import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.db.entities.TranslationResult;
import com.tinkoff.translator.db.repositories.TranslationResultRepository;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;
import com.tinkoff.translator.mappers.MessageMapper;
import com.tinkoff.translator.mappers.TranslationMapper;
import com.tinkoff.translator.mappers.TranslationResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    private TranslatorClient<YaMessageDto, YaTranslationDto> client;
    private TranslationResultRepository repository;
    private TranslationResultMapper translationResultMapper;
    private MessageMapper messageMapper;
    private TranslationMapper translationMapper;

    public TranslationService(@Autowired TranslatorClient<YaMessageDto, YaTranslationDto> client,
                              @Autowired TranslationResultMapper translationResultMapper,
                              @Autowired MessageMapper messageMapper,
                              @Autowired TranslationResultRepository repository,
                              @Autowired TranslationMapper translationMapper) {
        this.client = client;
        this.repository = repository;
        this.translationResultMapper = translationResultMapper;
        this.messageMapper = messageMapper;
        this.translationMapper = translationMapper;
    }

    public TranslationDto serve(MessageDto messageDto, int start, String clientIp) throws SQLException {
        YaMessageDto yaMessageDto = messageMapper.toYaDto(messageDto);
        YaTranslationDto yaTranslationDto = client.translate(yaMessageDto);
        List<TranslationResult> resultList = translationResultMapper.toTranslationResult(yaTranslationDto, yaMessageDto, LocalTime.now().getNano() - start, clientIp);
        for (TranslationResult result : resultList) {
            repository.save(result);
        }
        return translationMapper.toServiceDto(yaTranslationDto, messageDto);
    }
}
