package com.tinkoff.translator.client;

import com.tinkoff.translator.client.dto.IamTokenDto;
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

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class YandexTranslatorClient implements TranslatorClient<YaMessageDto, YaTranslationDto> {
    private static final String BASE_API_URL = "https://translate.api.cloud.yandex.net";
    private static final String TRANSLATE_API_URL = "/translate/v2/translate";
    private static final long TIMEOUT_IN_SECONDS = 20;
    private static final int MAX_THREADS = 10;
    public static final int MIN_THREADS = 1;
    private final IamTokenDto token;
    @Value("${yandex.folder-id}")
    private String folderId;
    private RestTemplate template = new RestTemplate();

    public YaTranslationDto translate(YaMessageDto messageDto) throws RestClientException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getIamToken());
        messageDto.setFolderId(folderId);
        return assembleYaTranslationDto(requestInThreads(messageDto, headers));
    }

    private List<YaTranslationDto> requestInThreads(YaMessageDto messageDto, HttpHeaders headers) throws InterruptedException {
        List<YaMessageDto> list = divideYaMessageDto(messageDto);
        List<Future<YaTranslationDto>> futures = new ArrayList<>();
        ExecutorService service = new ThreadPoolExecutor(MIN_THREADS, MAX_THREADS, TIMEOUT_IN_SECONDS, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
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
        return messageDto.getTexts().stream().map(x -> YaMessageDto.builder().sourceLanguage(messageDto.getSourceLanguage())
                .targetLanguage(messageDto.getTargetLanguage())
                .folderId(messageDto.getFolderId())
                .texts(List.of(x)).build()).toList();
    }

    private YaTranslationDto assembleYaTranslationDto(List<YaTranslationDto> list) {
        YaTranslationDto translationDto = new YaTranslationDto(new ArrayList<>());
        for (YaTranslationDto dto : list) {
            if (dto.getTranslations() != null) translationDto.getTranslations().addAll(dto.getTranslations());
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








