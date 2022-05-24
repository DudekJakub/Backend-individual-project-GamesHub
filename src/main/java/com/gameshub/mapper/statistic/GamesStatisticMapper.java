package com.gameshub.mapper.statistic;

import com.gameshub.domain.statistics.GamesStatistic;
import com.gameshub.domain.statistics.GamesStatisticDto;
import com.gameshub.domain.statistics.StatisticResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GamesStatisticMapper implements StatisticMapper {

    public GamesStatisticDto mapToGamesStatisticDto(final GamesStatistic statistic) {
        return GamesStatisticDto.builder()
                .statId(statistic.getId())
                .addedDate(statistic.getAddedDate())
                .qnt(statistic.getQnt())
                .opinionsQnt(statistic.getOpinionsQnt())
                .withLowPopStatus(statistic.getWithLowPopStatus())
                .withMediumPopStatus(statistic.getWithMediumPopStatus())
                .withHighPopStatus(statistic.getWithHighPopStatus())
                .withVeryHighPopStatus(statistic.getWithVeryHighPopStatus())
                .withOnTheTopPopStatus(statistic.getWithOnTheTopPopStatus())
                .withAvgRatingLowerThenFive(statistic.getWithAvgRatingLowerThenFive())
                .withAvgRatingBetween5And7_5(statistic.getWithAvgRatingBetween5And7_5())
                .withAvgRatingBetween7_5And8_9(statistic.getWithAvgRatingBetween7_5And8_9())
                .withAvgRatingHigherThen9(statistic.getWithAvgRatingHigherThen9())
                .build();
    }

    public List<GamesStatisticDto> mapToGamesStatisticListDtos(final List<GamesStatistic> statistics) {
        return statistics.stream()
                .map(this::mapToGamesStatisticDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticResource> mapToStatisticResourceList(final List<?> statistics) {
        return statistics.stream()
                .map(stat -> mapToGamesStatisticDto((GamesStatistic) stat))
                .collect(Collectors.toList());
    }
}
