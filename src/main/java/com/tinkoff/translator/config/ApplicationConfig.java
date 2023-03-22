package com.tinkoff.translator.config;

import com.tinkoff.translator.client.TokenClient;
import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.YandexTokenClient;
import com.tinkoff.translator.client.YandexTranslatorClient;
import com.tinkoff.translator.client.dto.IamTokenDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.OAuthToken;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    @Scope("token")
    public IamTokenDto getIamToken(TokenClient<IamTokenDto> client) {
        return client.createToken();
    }

    @Bean
    public OAuthToken getOAuthToken() {
        return new OAuthToken();
    }

    @Bean
    public TokenClient<IamTokenDto> getTokenClient() {
        return new YandexTokenClient(new RestTemplate(), getOAuthToken());
    }

    @Bean
    public TranslatorClient<YaMessageDto, YaTranslationDto> getTranslationClient() {
        return new YandexTranslatorClient(getIamToken(getTokenClient()));
    }
}
