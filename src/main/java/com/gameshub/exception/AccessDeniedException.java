package com.gameshub.exception;

public class AccessDeniedException extends Exception {

    private static final String ACCESS_DENIED = "Access denied! You have no permission to access that resource!";

    public AccessDeniedException() {
        super(ACCESS_DENIED);
    }
}
