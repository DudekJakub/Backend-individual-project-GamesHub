package com.gameshub.exceptions;

public class UserLoginNameAlreadyExistsInDatabaseException extends Exception {

    private static final String LOGIN_NAME_ALREADY_EXISTS = "Login name already exists in database.";

    public UserLoginNameAlreadyExistsInDatabaseException() {
        super(LOGIN_NAME_ALREADY_EXISTS);
    }
}
