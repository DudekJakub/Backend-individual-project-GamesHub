package com.gameshub.exception;

public class GameOpinionUpdateAccessDeniedException extends Exception {

    private static final String ACCESS_DENIED_FOR_OPINION_UPDATE = "Access denied! Current logged user has no authority to update given opinion!";

    public GameOpinionUpdateAccessDeniedException() {
        super(ACCESS_DENIED_FOR_OPINION_UPDATE);
    }
}
