package com.gameshub.exceptions;

public class UserNotFoundException extends Exception {

    private static final String USER_NOT_FOUND = "Given user doesn't exist in database!";

    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
