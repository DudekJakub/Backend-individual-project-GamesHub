package com.gameshub.exception;

public class AppNotifNotFoundException extends Exception {

    private static final String NOTIF_NOT_FOUND = "Notification not found!";

    public AppNotifNotFoundException() {
        super(NOTIF_NOT_FOUND);
    }
}
