package com.gameshub.exception;

public class GameOpinionWithProfanitiesException extends Exception {

    private static final String OPINION_CONTAINS_PROFANITIES = "Given opinion contains profanities!";

    public GameOpinionWithProfanitiesException() {
        super(OPINION_CONTAINS_PROFANITIES);
    }
}
