package com.gameshub.exception;

public class GameNotFoundException extends Exception {

    private static final String GAME_NOT_FOUND = "Couldn't find game by id!";

    public GameNotFoundException() {
        super(GAME_NOT_FOUND);
    }
}
