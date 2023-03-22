package com.tinkoff.translator.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IamTokenDto {

    private String iamToken;
    private LocalDateTime expiresAt;
}
