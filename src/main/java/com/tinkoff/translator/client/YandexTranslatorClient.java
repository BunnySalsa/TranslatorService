package com.tinkoff.translator.client;

import com.tinkoff.translator.client.dto.IamTokenDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YandexTranslatorClient implements TranslatorClient<YaMessageDto, YaTranslationDto> {
    private static final String BASE_API_URL = "https://translate.api.cloud.yandex.net";
    private static final String TRANSLATE_API_URL = "/translate/v2/translate";

    private IamTokenDto token;
    @Value("${yandex.folder-id}")
    private String folderId;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private RestTemplate template = new RestTemplate();

    public YandexTranslatorClient(@Autowired IamTokenDto token) {
        this.token = token;
    }

    public YaTranslationDto translate(YaMessageDto message) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getIamToken());
        message.setFolderId(folderId);
        HttpEntity<YaMessageDto> request = new HttpEntity<>(message, headers);
        return template.postForObject(BASE_API_URL + TRANSLATE_API_URL, request, YaTranslationDto.class);
    }

}








