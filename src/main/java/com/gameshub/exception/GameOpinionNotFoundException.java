package com.gameshub.exception;

public class GameOpinionNotFoundException extends Exception {

    private static final String OPINION_NOT_FOUND = "Couldn't find game opinion by given id!";

    public GameOpinionNotFoundException() {
        super(OPINION_NOT_FOUND);
    }
}
