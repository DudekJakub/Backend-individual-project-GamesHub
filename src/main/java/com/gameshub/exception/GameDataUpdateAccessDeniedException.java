package com.gameshub.exception;

public class GameDataUpdateAccessDeniedException extends Exception {

    private static final String ACCESS_DENIED_FOR_GAME_DATA_UPDATE = "Access denied! Current logged user has no authority to update given data!";

    public GameDataUpdateAccessDeniedException() {
        super(ACCESS_DENIED_FOR_GAME_DATA_UPDATE);
    }
}
