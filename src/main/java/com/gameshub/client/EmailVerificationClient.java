package com.gameshub.client;

import com.gameshub.client.config.EmailApiConfig;
import com.gameshub.domain.mail.MailVerificationDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailVerificationClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerificationClient.class);

    private final RestTemplate restTemplate;
    private final EmailApiConfig emailApiConfig;

    private URI CHECK_EMAIL_URI(final String email) {
        return UriComponentsBuilder.fromHttpUrl(emailApiConfig.getEmailVerificationApiEndpoint())
                .queryParam("apiKey", emailApiConfig.getEmailVerificationAppKey())
                .queryParam("emailAddress", email)
                .build()
                .encode()
                .toUri();
    }

    public Optional<MailVerificationDto> checkEmail(final String email) {
        URI url = CHECK_EMAIL_URI(email);
        try {
            MailVerificationDto mailCheckResponse = restTemplate.getForObject(url, MailVerificationDto.class);
            return Optional.ofNullable(mailCheckResponse);
        } catch (NoSuchElementException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
