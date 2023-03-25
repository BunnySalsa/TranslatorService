package com.tinkoff.translator.config;

import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.YandexTranslatorClient;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.repositories.RequestRepository;
import com.tinkoff.translator.repositories.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

@Configuration
public class ApplicationConfig {

    @Bean
    @Scope(scopeName = "prototype")
    public RequestRepository getRequestRepository(@Autowired Connection connection) {
        return new RequestRepository(connection);
    }

    @Bean
    @Scope(scopeName = "prototype")
    public TranslationRepository getTranslationRepository(@Autowired Connection connection) {
        return new TranslationRepository(connection);
    }

    @Bean
    @Scope(scopeName = "prototype")
    public TranslatorClient<YaMessageDto, YaTranslationDto> getTranslationClient() {
        return new YandexTranslatorClient();
    }

    @Bean
    @Scope(scopeName = "prototype")
    public Connection getConnection(Environment environment) throws SQLException {
        return DriverManager.getConnection(Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                environment.getProperty("spring.datasource.username"),
                environment.getProperty("spring.datasource.password"));
    }
}
