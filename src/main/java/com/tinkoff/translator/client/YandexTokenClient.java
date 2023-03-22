package com.tinkoff.translator.client;

import com.tinkoff.translator.client.dto.IamTokenDto;
import com.tinkoff.translator.client.dto.OAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Scope(scopeName = "prototype")
public class YandexTokenClient implements TokenClient<IamTokenDto>{
    private static final String CREATE_IAM_TOKEN_URL = "https://iam.api.cloud.yandex.net/iam/v1/tokens";
    private final RestTemplate restTemplate;
    private final OAuthToken oAuthToken;

    public YandexTokenClient(@Autowired RestTemplate restTemplate, @Autowired OAuthToken oAuthToken) {
        this.restTemplate = restTemplate;
        this.oAuthToken = oAuthToken;
    }

    public IamTokenDto createToken() {
        return restTemplate.postForObject(CREATE_IAM_TOKEN_URL,
                oAuthToken,
                IamTokenDto.class);
    }

}
