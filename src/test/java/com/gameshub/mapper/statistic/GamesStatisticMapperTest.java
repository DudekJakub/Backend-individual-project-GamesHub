package com.gameshub.mapper.statistic;

import com.gameshub.domain.statistics.GamesStatistic;
import com.gameshub.domain.statistics.GamesStatisticDto;
import com.gameshub.domain.statistics.StatisticResource;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GamesStatisticMapperTest {

    private final GamesStatisticMapper mapper = new GamesStatisticMapper();

    private GamesStatistic statistic;

    @BeforeEach
    void setSettings() {
        statistic = GamesStatistic.builder()
                .id(1L)
                .opinionsQnt(1)
                .qnt(2)
                .withAvgRatingBetween5And7_5(10)
                .withHighPopStatus(20)
                .build();
    }

    @Test
    void mapToGamesStatisticDto() {
        //Given
            //setSettings()

        //When
        GamesStatisticDto gamesStatisticDto = mapper.mapToGamesStatisticDto(statistic);

        //Then
        assertEquals(statistic.getId(), gamesStatisticDto.getStatId());
        assertEquals(statistic.getOpinionsQnt(), gamesStatisticDto.getOpinionsQnt());
        assertEquals(statistic.getQnt(), gamesStatisticDto.getQnt());
        assertEquals(statistic.getWithAvgRatingBetween5And7_5(), gamesStatisticDto.getWithAvgRatingBetween5And7_5());
        assertEquals(statistic.getWithHighPopStatus(), gamesStatisticDto.getWithHighPopStatus());
    }

    @Test
    void mapToGamesStatisticListDtos() {
        //Given
            //setSettings()

        List<GamesStatistic> statistics = List.of(statistic);

        //When
        List<GamesStatisticDto> dtos = mapper.mapToGamesStatisticListDtos(statistics);

        //Then
        assertEquals(statistics.get(0).getId(), dtos.get(0).getStatId());
        assertEquals(statistics.get(0).getOpinionsQnt(), dtos.get(0).getOpinionsQnt());
        assertEquals(statistics.get(0).getQnt(), dtos.get(0).getQnt());
        assertEquals(statistics.get(0).getWithAvgRatingBetween5And7_5(), dtos.get(0).getWithAvgRatingBetween5And7_5());
        assertEquals(statistics.get(0).getWithHighPopStatus(), dtos.get(0).getWithHighPopStatus());
    }

    @Test
    void mapToStatisticResourceList() {
        //Given
            //setSettings()

        List<GamesStatistic> statistics = List.of(statistic);

        //When
        List<StatisticResource> dtos = mapper.mapToStatisticResourceList(statistics);

        //Then
        List<Integer> allStatValueFromStatistic = List.of(statistic.getQnt(), statistic.getOpinionsQnt(), statistic.getWithAvgRatingBetween5And7_5(), statistic.getWithHighPopStatus());

        assertTrue(dtos.get(0).getAllStatValues().containsAll(allStatValueFromStatistic));
    }
}