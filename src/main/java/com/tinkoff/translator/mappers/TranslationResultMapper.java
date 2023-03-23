package com.tinkoff.translator.mappers;

import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.db.entities.TranslationResult;

import java.util.ArrayList;
import java.util.List;

public class TranslationResultMapper {

    public List<TranslationResult> toTranslationResult(Long request, YaTranslationDto translationDto,
                                                       YaMessageDto messageDto) {
        List<TranslationResult> resultList = new ArrayList<>();
        List<String> texts = messageDto.getTexts();
        List<TextDto> translations = translationDto.getTranslations();
        for (int i = 0; i < translations.size(); i++) {
            TranslationResult translationResult = TranslationResult.builder()
                    .request(request)
                    .sourceLang(translations.get(i).getDetectedLanguageCode() == null ? messageDto.getSourceLang() :
                            translations.get(i).getDetectedLanguageCode())
                    .targetLang(messageDto.getTargetLang())
                    .sourceText(texts.get(i))
                    .targetText(translations.get(i).getText())
                    .build();
            resultList.add(translationResult);
        }
        return resultList;
    }
}
