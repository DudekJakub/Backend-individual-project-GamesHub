package com.gameshub.exception;

public class GameAlreadyRatedByUserException extends Exception {

    private static final String GAME_ALREADY_RATED_BY_USER = "Given game has been already rated by user!";

    public GameAlreadyRatedByUserException() {
        super(GAME_ALREADY_RATED_BY_USER);
    }
}
