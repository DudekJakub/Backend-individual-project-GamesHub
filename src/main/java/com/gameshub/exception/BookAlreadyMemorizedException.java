package com.gameshub.exception;

public class BookAlreadyMemorizedException extends Exception {

    private static final String ALREADY_MEMORIZED = "Book already memorized";

    public BookAlreadyMemorizedException() {
        super(ALREADY_MEMORIZED);
    }
}
