package com.gameshub.exception;

public class RawgGameDetailedNotFoundException extends Exception{

    private static final String RAWG_GAME_NOT_FOUND = "Couldn't find RAWG's game by given id/slug-name!";

    public RawgGameDetailedNotFoundException() {
        super(RAWG_GAME_NOT_FOUND);
    }
}
