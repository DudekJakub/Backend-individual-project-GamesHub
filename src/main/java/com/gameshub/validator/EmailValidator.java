package com.gameshub.validator;

import com.gameshub.exception.UserEmailAlreadyExistsInDatabaseException;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailValidator.class);

    private final UserRepository userRepository;

    public boolean validateIfEmailAlreadyExistInDatabase(final String email, final String operationName) throws UserEmailAlreadyExistsInDatabaseException {
        boolean doesEmailExistInDatabase = userRepository.findByEmail(email).isPresent();

        if (doesEmailExistInDatabase) {
            LOGGER.error(operationName + "Validation result : email already exist in database!");
            throw new UserEmailAlreadyExistsInDatabaseException();
        }
        return false;
    }
}
