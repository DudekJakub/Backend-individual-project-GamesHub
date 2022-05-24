package com.gameshub.exception;

public class GoogleBooksNotFoundException extends Exception {

    private static final String BOOKS_NOT_FOUND = "Couldn't find books for given game!";

    public GoogleBooksNotFoundException() {
        super(BOOKS_NOT_FOUND);
    }
}
