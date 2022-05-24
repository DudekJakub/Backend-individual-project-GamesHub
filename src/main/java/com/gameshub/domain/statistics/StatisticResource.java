package com.gameshub.domain.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public interface StatisticResource {

    @JsonIgnore
    List<Integer> getAllStatValues();
}
