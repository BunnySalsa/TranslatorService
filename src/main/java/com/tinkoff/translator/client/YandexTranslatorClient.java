package com.tinkoff.translator.client;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinkoff.translator.client.dto.MessageDto;
import com.tinkoff.translator.client.dto.TranslationDto;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class YandexTranslatorClient {
    private static final String BASE_API_URL = "https://translate.api.cloud.yandex.net";
    private static final String TRANSLATE_API_URL = "/translate/v2/translate";

    private String token;
    private String folderId;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private RestTemplate template = new RestTemplate();

    public TranslationDto translate(MessageDto message) {
        //TODO Исправить хардкод
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("t1.9euelZqRjYuOmJWNmZGUmsuJiYuZie3rnpWanIyNm4mYkJCVjJuMk57Kx5vl9PcDJhhf-e9EQXfq3fT3Q1QVX_nvREF36g.NTonhTCFZ97kdpFB79NMpCeBF8R2WOf1oq3yEWJLxnmSdO2Daryh_BTL6gS05ob5IS-BHwguwPgorD2Je9e4AA");
        HttpEntity<MessageDto> request = new HttpEntity<>(message, headers);
        //TODO Убрать слэши, когда появятся деньги. 9 копеек - это 9 копеек
        return null;//template.postForObject(BASE_API_URL + TRANSLATE_API_URL, request, TranslationDto.class);
    }

}








