package com.tinkoff.translator.mappers;

import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.db.entities.TranslationResult;
import com.tinkoff.translator.dto.MessageDto;

import java.util.ArrayList;
import java.util.List;

public class TranslationResultMapper {

    public List<TranslationResult> toTranslationResult(YaTranslationDto translationDto, YaMessageDto messageDto, int answerTime, String clientIp) {
        List<TranslationResult> resultList = new ArrayList<>();
        List<String> texts = messageDto.getTexts();
        List<TextDto> translations = translationDto.getTranslations();
        for (int i = 0; i < translations.size(); i++) {
            TranslationResult translationResult = TranslationResult.builder()
                    .sourceLang(translations.get(i).getDetectedLanguageCode() == null ? messageDto.getSourceLang() : translations.get(i).getDetectedLanguageCode())
                    .targetLang(messageDto.getTargetLang())
                    .sourceText(texts.get(i))
                    .targetText(translations.get(i).getText())
                    .answerTime(answerTime)
                    .clientIp(clientIp)
                    .build();
            resultList.add(translationResult);
        }
        return resultList;
    }
}
