package com.tinkoff.translator.services;

import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.entities.RequestEntity;
import com.tinkoff.translator.entities.TranslationEntity;
import com.tinkoff.translator.mappers.RequestMapper;
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

    public void save(String clientIp, YaMessageDto yaMessageDto, YaTranslationDto yaTranslationDto) {
        RequestEntity request = new RequestMapper().toRequest(OffsetDateTime.now(), clientIp);
        requestRepository.save(request);
        List<TranslationEntity> resultList = new TranslationMapper()
                .toTranslationResultList(request.getId(), yaTranslationDto, yaMessageDto);
        for (TranslationEntity result : resultList) {
            translationRepository.save(result);
        }
    }
}
