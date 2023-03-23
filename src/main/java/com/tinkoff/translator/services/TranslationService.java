package com.tinkoff.translator.services;

import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.db.entities.RequestEntity;
import com.tinkoff.translator.db.entities.TranslationResult;
import com.tinkoff.translator.db.repositories.QueryRepository;
import com.tinkoff.translator.db.repositories.TranslationResultRepository;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;
import com.tinkoff.translator.mappers.MessageMapper;
import com.tinkoff.translator.mappers.RequestMapper;
import com.tinkoff.translator.mappers.TranslationMapper;
import com.tinkoff.translator.mappers.TranslationResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

@Service
public class TranslationService {

    private TranslatorClient<YaMessageDto, YaTranslationDto> client;
    private TranslationResultRepository translationResultRepository;
    private QueryRepository queryRepository;
    private TranslationResultMapper translationResultMapper;
    private MessageMapper messageMapper;
    private TranslationMapper translationMapper;
    private RequestMapper requestMapper;

    public TranslationService(@Autowired TranslatorClient<YaMessageDto, YaTranslationDto> client,
                              @Autowired TranslationResultMapper translationResultMapper,
                              @Autowired MessageMapper messageMapper,
                              @Autowired RequestMapper requestMapper,
                              @Autowired TranslationMapper translationMapper,
                              @Autowired QueryRepository queryRepository,
                              @Autowired TranslationResultRepository translationResultRepository) {
        this.client = client;
        this.translationResultRepository = translationResultRepository;
        this.queryRepository = queryRepository;
        this.translationResultMapper = translationResultMapper;
        this.messageMapper = messageMapper;
        this.translationMapper = translationMapper;
        this.requestMapper = requestMapper;
    }

    public TranslationDto serve(MessageDto messageDto, int start, String clientIp) throws SQLException {
        YaMessageDto yaMessageDto = messageMapper.toYaDto(messageDto);
        YaTranslationDto yaTranslationDto = client.translate(yaMessageDto);
        RequestEntity request = requestMapper.toQuery( LocalTime.now().getNano()/1000 - start, clientIp);
        queryRepository.save(request);
        List<TranslationResult> resultList = translationResultMapper
                .toTranslationResult(request.getId(),yaTranslationDto, yaMessageDto);
        for (TranslationResult result : resultList) {
            translationResultRepository.save(result);
        }
        return translationMapper.toServiceDto(yaTranslationDto, messageDto);
    }
}
