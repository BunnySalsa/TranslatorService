package com.tinkoff.translator.config;

import com.tinkoff.translator.client.TokenClient;
import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.YandexTokenClient;
import com.tinkoff.translator.client.YandexTranslatorClient;
import com.tinkoff.translator.client.dto.IamTokenDto;
import com.tinkoff.translator.client.dto.OAuthToken;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.mappers.MessageDtoMapper;
import com.tinkoff.translator.mappers.RequestMapper;
import com.tinkoff.translator.mappers.TranslationDtoMapper;
import com.tinkoff.translator.mappers.TranslationMapper;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

@Configuration
public class ApplicationConfig {

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new CustomScopeRegistryBeanFactoryPostProcessor();
    }

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
    @Scope(scopeName = "prototype")
    public TranslatorClient<YaMessageDto, YaTranslationDto> getTranslationClient() {
        return new YandexTranslatorClient(getIamToken(getTokenClient()));
    }

    @Bean
    @Scope(scopeName = "prototype")
    public Connection getConnection(Environment environment) throws SQLException {
        return DriverManager.getConnection(Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                environment.getProperty("spring.datasource.username"),
                environment.getProperty("spring.datasource.password"));
    }

    @Bean
    public MessageDtoMapper getMessageMapper() {
        return new MessageDtoMapper();
    }

    @Bean
    public TranslationDtoMapper getTranslationMapper() {
        return new TranslationDtoMapper();
    }

    @Bean
    public TranslationMapper getTranslationResultMapper() {
        return new TranslationMapper();
    }

    @Bean
    public RequestMapper getQueryMapper() {
        return new RequestMapper();
    }
}
