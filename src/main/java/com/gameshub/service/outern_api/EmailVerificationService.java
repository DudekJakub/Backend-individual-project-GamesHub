package com.gameshub.service.outern_api;

import com.gameshub.client.EmailVerificationClient;
import com.gameshub.exception.EmailVerificationFailedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerificationService.class);

    private final EmailVerificationClient emailVerificationClient;

    public boolean checkEmailExists(final String email) throws EmailVerificationFailedException {
        LOGGER.info("Verifying e-mail...");
        boolean emailExists = emailVerificationClient.checkEmail(email)
                                                    .orElseThrow(EmailVerificationFailedException::new)
                                                    .isEmailExists();
        LOGGER.info("Verifying e-mail done successfully!");

        return emailExists;
    }
}
