package com.gameshub.service.statistic;

import com.gameshub.domain.statistics.GamesStatistic;
import com.gameshub.domain.statistics.GamesStatisticDto;
import com.gameshub.exception.GamesStatisticNotFound;
import com.gameshub.exception.StatisticNotFound;
import com.gameshub.mapper.statistic.GamesStatisticMapper;
import com.gameshub.repository.GamesStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GamesStatisticService implements StatisticService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GamesStatisticService.class);

    private final GamesStatisticRepository gamesStatRepository;
    private final GamesStatisticMapper gamesStatMapper;

    public GamesStatisticDto createAndGetNewStatistic() throws StatisticNotFound {
        try {
            LOGGER.info("Creating new games statistic...");
            gamesStatRepository.createNewGamesStats();
            LOGGER.info("Statistic created successfully!");
        } catch (Exception e) {
            LOGGER.warn("Statistic creating failed! Exception occurred! " + e.getMessage());
        }
        GamesStatistic statistic = gamesStatRepository.retrieveTheNewestStats().orElseThrow(StatisticNotFound::new);
        return gamesStatMapper.mapToGamesStatisticDto(statistic);
    }

    public GamesStatisticDto getStatisticById(final Long gamesStatsId) throws GamesStatisticNotFound {
        GamesStatistic gamesStatistic = gamesStatRepository.findById(gamesStatsId).orElseThrow(GamesStatisticNotFound::new);
        return gamesStatMapper.mapToGamesStatisticDto(gamesStatistic);
    }

    public List<GamesStatisticDto> getStatistics() {
        List<GamesStatistic> statistics = gamesStatRepository.findAll();
        return gamesStatMapper.mapToGamesStatisticListDtos(statistics);
    }
}
