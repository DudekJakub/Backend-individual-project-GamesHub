package com.gameshub.exception;

public class BookNotFoundException extends Exception {

    private static final String BOOK_NOT_FOUND = "Book not found in database!";

    public BookNotFoundException() {
        super(BOOK_NOT_FOUND);
    }
}
