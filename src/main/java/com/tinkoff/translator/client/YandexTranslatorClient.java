package com.tinkoff.translator.client;

import com.tinkoff.translator.client.dto.IamTokenDto;
import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.StreamSupport;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class YandexTranslatorClient implements TranslatorClient<YaMessageDto, YaTranslationDto> {
    private static final String BASE_API_URL = "https://translate.api.cloud.yandex.net";
    private static final String TRANSLATE_API_URL = "/translate/v2/translate";
    private static final long TIMEOUT_IN_SECONDS = 20;
    private static final int MAX_THREADS = 10;
    private IamTokenDto token;
    @Value("${yandex.folder-id}")
    private String folderId;
    @Value("${yandex.api-key}")
    private String apiKey;
    private RestTemplate template = new RestTemplate();

    public YaTranslationDto translate(YaMessageDto messageDto) throws RestClientException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", apiKey);
        return assembleYaTranslationDto(requestInThreads(messageDto, headers));
    }

    private List<YaTranslationDto> requestInThreads(YaMessageDto messageDto, HttpHeaders headers) throws InterruptedException {
        List<YaMessageDto> list = divideYaMessageDto(messageDto);
        List<Future<YaTranslationDto>> futures = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREADS);
        for (YaMessageDto dto : list) {
            futures.add(service.submit(new RequestCallable(dto, headers)));
        }
        service.shutdown();
        List<YaTranslationDto> translationDtoList = new ArrayList<>();
        if (service.awaitTermination(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)) {
            translationDtoList.addAll(futures.stream().map(this::saveGet).toList());
        }
        return translationDtoList;
    }

    private YaTranslationDto saveGet(Future<YaTranslationDto> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException exception) {
            Thread.currentThread().interrupt();
            return new YaTranslationDto();
        }
    }

    private List<YaMessageDto> divideYaMessageDto(YaMessageDto messageDto) {
        List<YaMessageDto> result = new ArrayList<>();
        for (int i = 0; i < MAX_THREADS; i++) {
            result.add(YaMessageDto.builder().sourceLanguage(messageDto.getSourceLanguage())
                    .targetLanguage(messageDto.getTargetLanguage())
                    .texts(new ArrayList<>()).build());
        }
        int i = 0;
        for (String s : messageDto.getTexts()) {
            result.get(i).getTexts().add(s);
            i = i < MAX_THREADS - 1 ? i + 1 : 0;
        }
        return result;
    }

    private YaTranslationDto assembleYaTranslationDto(List<YaTranslationDto> list) {
        YaTranslationDto translationDto = new YaTranslationDto(new ArrayList<>());
        int i = 0;
        int j = 0;
        while (i < list.get(j).getTranslations().size()) {
            while (j < MAX_THREADS) {
                if (i < list.get(j).getTranslations().size())
                    translationDto.getTranslations().add(list.get(j).getTranslations().get(i));
                else break;
                j++;
            }
            j = 0;
            i++;
        }
        return translationDto;
    }

    public class RequestCallable implements Callable<YaTranslationDto> {
        private final YaMessageDto messageDto;
        private final HttpHeaders headers;

        public RequestCallable(YaMessageDto messageDto, HttpHeaders headers) {
            this.messageDto = messageDto;
            this.headers = headers;
        }

        @Override
        public YaTranslationDto call() {
            return template.postForObject(BASE_API_URL + TRANSLATE_API_URL,
                    new HttpEntity<>(messageDto, headers), YaTranslationDto.class);
        }
    }
}








