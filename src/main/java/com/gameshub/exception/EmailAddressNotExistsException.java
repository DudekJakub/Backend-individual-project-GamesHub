package com.gameshub.exception;

public class EmailAddressNotExistsException extends Exception {

    private static final String ADDRESS_NOT_EXISTS = "Given e-mail's SMTP status doesn't exist! Please insert different e-mail address!";

    public EmailAddressNotExistsException() {
        super(ADDRESS_NOT_EXISTS);
    }
}
