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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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

    public YaTranslationDto translate(YaMessageDto message) throws RestClientException, ExecutionException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getIamToken());
        message.setFolderId(folderId);
        YaTranslationDto result = new YaTranslationDto();
        List<YaMessageDto> list = message.getTexts().stream().map(x -> YaMessageDto.builder().sourceLang(message.getSourceLang())
                .targetLang(message.getTargetLang())
                .folderId(message.getFolderId())
                .texts(List.of(x)).build()).toList();
        List<Future<YaTranslationDto>> futures = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (YaMessageDto messageDto : list) {
            futures.add(service.submit(new RequestRunnable(messageDto, headers)));
        }
        for (Future<YaTranslationDto> dto : futures) {
            result.getTranslations().addAll(dto.get().getTranslations());
        }
        service.shutdown();
        return result;
    }

    public class RequestRunnable implements Callable<YaTranslationDto> {
        private final YaMessageDto messageDto;
        private final HttpHeaders headers;

        public RequestRunnable(YaMessageDto messageDto, HttpHeaders headers) {
            this.messageDto = messageDto;
            this.headers = headers;
        }

        @Override
        public YaTranslationDto call() throws Exception {
            return template.postForObject(BASE_API_URL + TRANSLATE_API_URL,
                    new HttpEntity<>(messageDto, headers), YaTranslationDto.class);
        }
    }
}








