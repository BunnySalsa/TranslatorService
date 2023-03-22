package com.tinkoff.translator.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthToken {
    @Value("${yandex.oauth.token}")
    private String yandexPassportOauthToken;
}
