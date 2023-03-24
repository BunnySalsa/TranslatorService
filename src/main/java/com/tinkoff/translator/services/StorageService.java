package com.tinkoff.translator.services;

import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.entities.RequestEntity;
import com.tinkoff.translator.entities.TranslationResultEntity;
import com.tinkoff.translator.mappers.TranslationMapper;
import com.tinkoff.translator.repositories.RequestRepository;
import com.tinkoff.translator.repositories.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final RequestRepository requestRepository;
    private final TranslationRepository translationRepository;
    private final TranslationMapper translationMapper;

    public void save(String clientIp, YaMessageDto yaMessageDto, YaTranslationDto yaTranslationDto) {
        RequestEntity request = RequestEntity.builder()
                .requestTime(OffsetDateTime.now())
                .clientIp(clientIp).build();
        requestRepository.save(request);
        List<TranslationResultEntity> resultList = translationMapper
                .toTranslationResultList(request.getId(), yaTranslationDto, yaMessageDto);
        for (TranslationResultEntity result : resultList) {
            translationRepository.save(result);
        }
    }
}
