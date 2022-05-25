package com.gameshub.validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEqualityValidatorTest {

    private PasswordEqualityValidator passValidator = new PasswordEqualityValidator();

    @Test
    void testBiPredicateTestMethod() {
        //Given
        String password = "admin";
        String repeatPassword = "admin";

        //When
        boolean passwordEquality = passValidator.test(password, repeatPassword);

        //Then
        assertTrue(passwordEquality);
    }
}