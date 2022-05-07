package com.gameshub.validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PasswordEqualityValidatorTest {

    @Autowired
    private PasswordEqualityValidator passValidator;

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