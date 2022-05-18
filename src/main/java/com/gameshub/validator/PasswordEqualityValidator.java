package com.gameshub.validator;

import com.gameshub.exception.PasswordNotMatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.BiPredicate;

@Service
public class PasswordEqualityValidator implements BiPredicate<String, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEqualityValidator.class);

    @Override
    public boolean test(final String password, final String repeatPassword) {
        return password.equals(repeatPassword);
    }

    public void validate(final String password, final String repeatPassword, final String operationName) throws PasswordNotMatchException {
        if (!test(password, repeatPassword)) {
            LOGGER.error(operationName + "Validation failed! Given password does not match with repeated password!");
            throw new PasswordNotMatchException();
        }
    }
}
