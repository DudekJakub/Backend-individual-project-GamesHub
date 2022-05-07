package com.gameshub.exceptions;

public class UserEmailAlreadyExistsInDatabaseException extends Exception {

    private static final String EMAIL_ALREADY_EXISTS = "Email already exists in database.";

    public UserEmailAlreadyExistsInDatabaseException() {
        super(EMAIL_ALREADY_EXISTS);
    }
}
