package com.gameshub.exception;

public class UsersStatisticNotFound extends Exception {

    private static final String STATISTICS_NOT_FOUND = "Couldn't find users statistic!";

    public UsersStatisticNotFound() {
        super(STATISTICS_NOT_FOUND);
    }
}
