package com.gameshub.mapper.statistic;

import com.gameshub.domain.statistics.StatisticResource;

import java.util.List;

public interface StatisticMapper {

    List<StatisticResource> mapToStatisticResourceList(final List<?> statistics);
}
