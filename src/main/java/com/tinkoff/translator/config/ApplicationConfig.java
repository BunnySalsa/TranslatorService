package com.tinkoff.translator.config;

import com.tinkoff.translator.client.TokenClient;
import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.YandexTokenClient;
import com.tinkoff.translator.client.YandexTranslatorClient;
import com.tinkoff.translator.client.dto.IamTokenDto;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.OAuthToken;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.db.entities.TranslationResult;
import com.tinkoff.translator.db.row_mappers.TranslationResultRowMapper;
import com.tinkoff.translator.mappers.MessageMapper;
import com.tinkoff.translator.mappers.TranslationMapper;
import com.tinkoff.translator.mappers.TranslationResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    @Bean
    @Scope(scopeName = "prototype")
    public Connection getConnection(Environment environment) throws SQLException {
        return DriverManager.getConnection(environment.getProperty("spring.datasource.url"),
                environment.getProperty("spring.datasource.username"),
                environment.getProperty("spring.datasource.password"));
    }

    @Bean
    public MessageMapper getMessageMapper() {
        return new MessageMapper();
    }

    @Bean
    public RowMapper<TranslationResult> getTranslationResultRowMapper() {
        return new TranslationResultRowMapper();
    }

    @Bean
    public TranslationMapper getTranslationMapper() {
        return new TranslationMapper();
    }

    @Bean
    public TranslationResultMapper getTranslationResultMapper() {
        return new TranslationResultMapper();
    }
}
