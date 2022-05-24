package com.gameshub.exception;

public class BookNotMemorizedException extends Exception {

    private static final String NOT_MEMORIZED_BY_USER = "User has not memorized given book!";

    public BookNotMemorizedException() {
        super(NOT_MEMORIZED_BY_USER);
    }
}
