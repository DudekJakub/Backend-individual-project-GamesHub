package com.gameshub.exception;

public class GameOpinionsListNotFoundException extends Exception {

    private static final String OPINIONS_NOT_FOUND = "Couldn't fetch opinions : game with given ID doesn't exist in database!";

    public GameOpinionsListNotFoundException() {
        super(OPINIONS_NOT_FOUND);
    }
}
