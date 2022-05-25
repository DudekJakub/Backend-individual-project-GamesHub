package com.gameshub.mapper.statistic;

import com.gameshub.domain.statistics.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersStatisticMapperTest {

    private final UsersStatisticMapper mapper = new UsersStatisticMapper();

    private UsersStatistic statistic;

    @BeforeEach
    void setSettings() {
        statistic = UsersStatistic.builder()
                .id(1L)
                .qnt(2)
                .withTopActivityStatus(10)
                .withOpinionsQntBetween5And10(10)
                .withOpinionsQntHigherThen10(20)
                .build();
    }


    @Test
    void mapToUsersStatisticDto() {
        //Given
            //setSettings()

        //When
        UsersStatisticDto usersStatisticDto = mapper.mapToUsersStatisticDto(statistic);

        //Then
        assertEquals(statistic.getId(), usersStatisticDto.getStatId());
        assertEquals(statistic.getWithTopActivityStatus(), usersStatisticDto.getWithTopActivityStatus());
        assertEquals(statistic.getQnt(), usersStatisticDto.getQnt());
        assertEquals(statistic.getWithOpinionsQntBetween5And10(), usersStatisticDto.getWithOpinionsQntBetween5And10());
        assertEquals(statistic.getWithOpinionsQntHigherThen10(), usersStatisticDto.getWithOpinionsQntHigherThen10());
    }

    @Test
    void mapToUsersStatisticListDtos() {
        //Given
            //setSettings()

        List<UsersStatistic> statistics = List.of(statistic);

        //When
        List<UsersStatisticDto> dtos = mapper.mapToUsersStatisticListDtos(statistics);

        //Then
        assertEquals(statistics.get(0).getId(), dtos.get(0).getStatId());
        assertEquals(statistics.get(0).getWithTopActivityStatus(), dtos.get(0).getWithTopActivityStatus());
        assertEquals(statistics.get(0).getQnt(), dtos.get(0).getQnt());
        assertEquals(statistics.get(0).getWithOpinionsQntBetween5And10(), dtos.get(0).getWithOpinionsQntBetween5And10());
        assertEquals(statistics.get(0).getWithOpinionsQntHigherThen10(), dtos.get(0).getWithOpinionsQntHigherThen10());
    }

    @Test
    void mapToStatisticResourceList() {
        //Given
        //setSettings()

        List<UsersStatistic> statistics = List.of(statistic);

        //When
        List<StatisticResource> dtos = mapper.mapToStatisticResourceList(statistics);

        //Then
        List<Integer> allStatValueFromStatistic = List.of(statistic.getQnt(), statistic.getWithTopActivityStatus(), statistic.getWithOpinionsQntBetween5And10(), statistic.getWithOpinionsQntHigherThen10());

        assertTrue(dtos.get(0).getAllStatValues().containsAll(allStatValueFromStatistic));
    }
}