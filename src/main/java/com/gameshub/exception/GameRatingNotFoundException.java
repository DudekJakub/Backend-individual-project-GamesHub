package com.gameshub.exception;

public class GameRatingNotFoundException extends Exception {

    private static final String RATING_NOT_FOUND = "Couldn't find game rating!";

    public GameRatingNotFoundException() {
        super(RATING_NOT_FOUND);
    }
}
