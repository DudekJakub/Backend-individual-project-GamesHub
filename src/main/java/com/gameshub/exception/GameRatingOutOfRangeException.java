package com.gameshub.exception;

public class GameRatingOutOfRangeException extends Exception {

    private static final String RATING_OUT_OF_RANGE = "Given rating is out of range [0.0 - 10.0]!";

    public GameRatingOutOfRangeException() {
        super(RATING_OUT_OF_RANGE);
    }
}
