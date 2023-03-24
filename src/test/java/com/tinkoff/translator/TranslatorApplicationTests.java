package com.tinkoff.translator;

import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.dto.TextDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class TranslatorApplicationTests {

    private final TranslatorClient<YaMessageDto, YaTranslationDto> client;

    TranslatorApplicationTests(@Autowired TranslatorClient<YaMessageDto, YaTranslationDto> client) {
        this.client = client;
    }


    @Test
    void clientTest() {
        Assert.notNull(client, "Client shouldn't be null");
        YaMessageDto messageDto = YaMessageDto.builder()
                .sourceLang("en").targetLang("ru").texts(List.of("Hello world")).build();
        try {
            YaTranslationDto result = client.translate(messageDto);
            Assert.notNull(result, "Answer shouldn't be null");
            YaTranslationDto expected = YaTranslationDto.builder().translations(List.of(
                    new TextDto("Привет, мир"))).build();
            Assert.isTrue(result.equals(expected), "Should be equals");
        } catch (InterruptedException | ExecutionException exception) {
            Assert.isTrue(false, "Thread exception");
        } catch (RestClientException e) {
            Assert.isTrue(false, "RestClientException");
        }
    }


}
