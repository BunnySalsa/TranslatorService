package com.tinkoff.translator.client;

import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class YandexTranslatorClient implements TranslatorClient<YaMessageDto, YaTranslationDto> {
    private static final long TIMEOUT_IN_SECONDS = 20;
    private static final int MAX_THREADS = 10;
    private static final int FIRST_DTO = 0;
    private static final String AUTHORIZATION = "Authorization";
    @Value("${yandex.api-key}")
    private String apiKey;
    @Value("${yandex.translator.url}")
    private String baseUrl;
    @Value("${yandex.translator.api.post}")
    private String translateApiUrl;
    private RestTemplate template = new RestTemplate();

    public YaTranslationDto translate(YaMessageDto messageDto) throws RestClientException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, apiKey);
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
            translationDtoList.addAll(futures.stream().map(this::safeGet).toList());
        }
        return translationDtoList;
    }

    private YaTranslationDto safeGet(Future<YaTranslationDto> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException exception) {
            Thread.currentThread().interrupt();
            return YaTranslationDto.builder().translations(new ArrayList<>()).build();
        }
    }

    private List<YaMessageDto> divideYaMessageDto(YaMessageDto messageDto) {
        int numThreads = Math.min(messageDto.getTexts().size(), MAX_THREADS);
        List<YaMessageDto> result = IntStream.range(0, numThreads).mapToObj(i -> YaMessageDto.builder()
                .sourceLanguage(messageDto.getSourceLanguage())
                .targetLanguage(messageDto.getTargetLanguage())
                .texts(new ArrayList<>()).build()).toList();
        IntStream.range(0, messageDto.getTexts().size())
                .forEach(i -> {
                    int index = i * numThreads / messageDto.getTexts().size();
                    result.get(index).getTexts().add(messageDto.getTexts().get(i));
                });
        return result;
    }

    private YaTranslationDto assembleYaTranslationDto(List<YaTranslationDto> list) {
        YaTranslationDto translationDto = new YaTranslationDto(new ArrayList<>());
        list.forEach(x -> translationDto.getTranslations().addAll(x.getTranslations()));
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
            return template.postForObject(baseUrl + translateApiUrl,
                    new HttpEntity<>(messageDto, headers), YaTranslationDto.class);
        }
    }
}








