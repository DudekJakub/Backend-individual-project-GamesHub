package com.gameshub.repository;

import com.gameshub.domain.statistics.GamesStatistic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class GamesStatisticRepositoryTest {

    @Autowired
    private GamesStatisticRepository gamesStatisticRepository;

    @Test
    void retrieveTheNewestStats() throws InterruptedException {
        //Given
        GamesStatistic gamesStatistic = GamesStatistic.builder()
                .qnt(3000)
                .opinionsQnt(2000)
                .addedDate(LocalDateTime.now())
                .build();

        Thread.sleep(1000);

        GamesStatistic gamesStatistic2 = GamesStatistic.builder()
                .qnt(4000)
                .opinionsQnt(5000)
                .addedDate(LocalDateTime.now())
                .build();

        gamesStatisticRepository.saveAll(List.of(gamesStatistic, gamesStatistic2));

        //Then
        GamesStatistic result = gamesStatisticRepository.retrieveTheNewestStats().orElseThrow();

        //Then
        assertEquals(gamesStatistic2.getId(), result.getId());
        assertEquals(gamesStatistic2.getQnt(), result.getQnt());
        assertEquals(gamesStatistic2.getOpinionsQnt(), result.getOpinionsQnt());
    }

    @Test
    void findAll() {
        //Given
        GamesStatistic gamesStatistic = GamesStatistic.builder()
                .qnt(3000)
                .opinionsQnt(2000)
                .addedDate(LocalDateTime.now())
                .build();

        GamesStatistic gamesStatistic2 = GamesStatistic.builder()
                .qnt(4000)
                .opinionsQnt(5000)
                .addedDate(LocalDateTime.now())
                .build();

        gamesStatisticRepository.saveAll(List.of(gamesStatistic, gamesStatistic2));

        //Then
        List<GamesStatistic> resultList = gamesStatisticRepository.findAll();

        //Then
        assertEquals(gamesStatistic.getId(), resultList.get(0).getId());
        assertEquals(gamesStatistic.getQnt(), resultList.get(0).getQnt());
        assertEquals(gamesStatistic.getOpinionsQnt(), resultList.get(0).getOpinionsQnt());
        assertEquals(gamesStatistic2.getId(), resultList.get(1).getId());
        assertEquals(gamesStatistic2.getQnt(), resultList.get(1).getQnt());
        assertEquals(gamesStatistic2.getOpinionsQnt(), resultList.get(1).getOpinionsQnt());
    }
}