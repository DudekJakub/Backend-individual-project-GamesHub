package com.gameshub.validator;

import com.gameshub.exceptions.PasswordNotMatchException;
import org.springframework.stereotype.Service;

import java.util.function.BiPredicate;

@Service
public class PasswordEqualityValidator implements BiPredicate<String, String> {

    @Override
    public boolean test(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }

    public void validate(String password, String repeatPassword) throws PasswordNotMatchException {
        if (!test(password, repeatPassword)) {
            throw new PasswordNotMatchException();
        }
    }
}
