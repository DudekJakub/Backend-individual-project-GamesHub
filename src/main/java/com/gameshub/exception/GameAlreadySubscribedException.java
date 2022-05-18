package com.gameshub.exception;

public class GameAlreadySubscribedException extends Exception {

    private static final String USER_ALREADY_SUBSCRIBED_GIVEN_GAME = "Game has been already subscribed by user!";

    public GameAlreadySubscribedException() {
        super(USER_ALREADY_SUBSCRIBED_GIVEN_GAME);
    }
}
