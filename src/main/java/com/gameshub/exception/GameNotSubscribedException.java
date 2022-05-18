package com.gameshub.exception;

public class GameNotSubscribedException extends Exception {

    private static final String USER_IS_NOT_SUBSCRIBING_GIVEN_GAME = "User is NOT subscribing given game!";

    public GameNotSubscribedException() {
        super(USER_IS_NOT_SUBSCRIBING_GIVEN_GAME);
    }
}
