package com.gameshub.exception;

public class StatisticNotFound extends Exception {

    private static final String STATISTIC_NOT_FOUND = "Statistics not found!";

    public StatisticNotFound() {
        super(STATISTIC_NOT_FOUND);
    }
}
