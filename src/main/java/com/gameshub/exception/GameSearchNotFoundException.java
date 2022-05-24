package com.gameshub.exception;

public class GameSearchNotFoundException extends Exception {

    private static final String SEARCH_WITH_NO_RESULT = "No such a game found in database.";

    public GameSearchNotFoundException() {
        super(SEARCH_WITH_NO_RESULT);
    }
}
