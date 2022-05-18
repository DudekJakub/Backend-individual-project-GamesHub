package com.gameshub.exception;

public class UserNotVerifiedException extends Exception {

    private static final String USER_NOT_VERIFIED = "The user has not verified his account via email!";

    public UserNotVerifiedException() {
        super(USER_NOT_VERIFIED);
    }
}
