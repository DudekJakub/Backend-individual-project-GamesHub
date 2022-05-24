package com.gameshub.facade;

import com.gameshub.domain.statistics.GamesStatistic;
import com.gameshub.domain.statistics.StatisticResource;
import com.gameshub.domain.statistics.UsersStatistic;
import com.gameshub.mapper.statistic.GamesStatisticMapper;
import com.gameshub.mapper.statistic.UsersStatisticMapper;
import com.gameshub.repository.GamesStatisticRepository;
import com.gameshub.repository.UsersStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatisticFacade {

    private final GamesStatisticRepository gamesStatRepository;
    private final UsersStatisticRepository usersStatRepository;

    private final GamesStatisticMapper gamesStatMapper;
    private final UsersStatisticMapper usersStatMapper;

    public List<List<StatisticResource>> fetchApplicationCombinedStatistics() {
        List<GamesStatistic> gamesStatistics = gamesStatRepository.findAll();
        List<UsersStatistic> usersStatistics = usersStatRepository.findAll();

        List<List<StatisticResource>> statistics = new ArrayList<>();
        statistics.add(gamesStatMapper.mapToStatisticResourceList(gamesStatistics));
        statistics.add(usersStatMapper.mapToStatisticResourceList(usersStatistics));

        return statistics;
    }
}
