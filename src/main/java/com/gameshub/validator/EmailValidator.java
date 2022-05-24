package com.gameshub.validator;

import com.gameshub.exception.EmailAddressNotExistsException;
import com.gameshub.exception.EmailVerificationFailedException;
import com.gameshub.exception.UserEmailAlreadyExistsInDatabaseException;
import com.gameshub.repository.UserRepository;
import com.gameshub.service.outern_api.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailValidator.class);

    private final UserRepository userRepository;
    private final EmailVerificationService emailVerificationService;

    public void validateEmailDatabaseAbsence(final String email, final String operationName) throws UserEmailAlreadyExistsInDatabaseException {
        boolean doesEmailExistInDatabase = userRepository.findByEmail(email).isPresent();

        if (doesEmailExistInDatabase) {
            LOGGER.warn(operationName + "Validation result : email already exist in database!");
            throw new UserEmailAlreadyExistsInDatabaseException();
        }
    }

    public void validateEmailSmtpStatus(final String email, final String operationName) throws EmailVerificationFailedException, EmailAddressNotExistsException {
        if (!emailVerificationService.checkEmailExists(email)) {
            LOGGER.warn(operationName + "Validation failed! Email's existence SMTP status is negative!");
            throw new EmailAddressNotExistsException();
        }
    }
}
