package com.gameshub.exception;

public class UserIsNotAdminException extends Exception {

    private static final String USER_IS_NOT_ADMIN = "Given User is NOT application ADMIN";

    public UserIsNotAdminException() {
        super(USER_IS_NOT_ADMIN);
    }
}
