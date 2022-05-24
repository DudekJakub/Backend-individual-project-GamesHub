package com.gameshub.exception;

public class GameOpinionLengthTooLongException extends Exception {

    private static final String OPINION_TOO_LONG = "Given opinion is too long!";

    public GameOpinionLengthTooLongException() {
        super(OPINION_TOO_LONG);
    }
}
