package com.gameshub.service.statistic;

import com.gameshub.domain.statistics.StatisticResource;
import com.gameshub.exception.StatisticNotFound;

public interface StatisticService {

    StatisticResource createAndGetNewStatistic() throws StatisticNotFound;
}
