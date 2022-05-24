package com.gameshub.exception;

public class GamesStatisticNotFound extends Exception {

    private static final String STATISTIC_NOT_FOUND = "Couldn't find games statistic!";

    public GamesStatisticNotFound() {
        super(STATISTIC_NOT_FOUND);
    }
}
