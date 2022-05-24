package com.gameshub.exception;

public class GoogleBookNotFoundException extends Exception {

    private static final String DETAILED_BOOK_NOT_FOUND = "Couldn't find detailed book!";

    public GoogleBookNotFoundException() {
        super(DETAILED_BOOK_NOT_FOUND);
    }
}
