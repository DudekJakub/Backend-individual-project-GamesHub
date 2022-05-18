package com.gameshub.exception;

public class GameOpinionUpdateTimeExpiredException extends Exception {

    private static final String OPINION_UPDATE_TIME_EXPIRED = "Opinion is too old to update!";

    public GameOpinionUpdateTimeExpiredException() {
        super(OPINION_UPDATE_TIME_EXPIRED);
    }
}
