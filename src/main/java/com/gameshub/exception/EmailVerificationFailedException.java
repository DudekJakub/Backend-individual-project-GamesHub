package com.gameshub.exception;

public class EmailVerificationFailedException extends Exception {

    private static final String EMAIL_VERIFY_FAILED = "Couldn't verify e-mail!";

    public EmailVerificationFailedException() {
        super(EMAIL_VERIFY_FAILED);
    }
}
