package com.gameshub.exception;

public class PasswordNotMatchException extends Exception {

    private static final String PASSWORD_MISMATCH = "Password and repeat password don't match!";

    public PasswordNotMatchException() {
        super(PASSWORD_MISMATCH);
    }
}
