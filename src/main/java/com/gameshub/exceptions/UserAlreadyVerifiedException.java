package com.gameshub.exceptions;

public class UserAlreadyVerifiedException extends Exception {

    private static final String ALREADY_VERIFIED = "User already verified.";

    public UserAlreadyVerifiedException() {
        super(ALREADY_VERIFIED);
    }
}
