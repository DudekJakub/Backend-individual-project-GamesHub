package com.gameshub.client.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EmailApiConfig {

    @Value("${email.verification.api.endpoint.prod}")
    private String emailVerificationApiEndpoint;

    @Value("${email.verification.app.key}")
    private String emailVerificationAppKey;
}
