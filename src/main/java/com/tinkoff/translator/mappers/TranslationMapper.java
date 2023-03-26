package com.tinkoff.translator.mappers;

import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.entities.TranslationEntity;

import java.util.ArrayList;
import java.util.List;

public class TranslationMapper {

    public List<TranslationEntity> toTranslationResultList(Long request, YaTranslationDto translationDto,
                                                           YaMessageDto messageDto) {
        List<TranslationEntity> resultList = new ArrayList<>();
        List<String> texts = messageDto.getTexts();
        List<TextDto> translations = translationDto.getTranslations();
        for (int i = 0; i < translations.size(); i++) {
            TranslationEntity translationEntity = TranslationEntity.builder()
                    .request(request)
                    .sourceLang(translations.get(i).getDetectedLanguageCode() == null ? messageDto.getSourceLanguage() :
                            translations.get(i).getDetectedLanguageCode())
                    .targetLang(messageDto.getTargetLanguage())
                    .sourceText(texts.get(i))
                    .targetText(translations.get(i).getText())
                    .build();
            resultList.add(translationEntity);
        }
        return resultList;
    }
}
