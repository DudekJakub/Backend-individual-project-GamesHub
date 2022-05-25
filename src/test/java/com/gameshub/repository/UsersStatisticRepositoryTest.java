package com.gameshub.repository;

import com.gameshub.domain.statistics.GamesStatistic;
import com.gameshub.domain.statistics.UsersStatistic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UsersStatisticRepositoryTest {

    @Autowired
    private UsersStatisticRepository usersStatisticRepository;

    @Test
    void retrieveTheNewestStats() throws InterruptedException {
        //Given
        UsersStatistic usersStatistic = UsersStatistic.builder()
                .qnt(3000)
                .admins(5)
                .addedDate(LocalDateTime.now())
                .build();

        Thread.sleep(1000);

        UsersStatistic usersStatistic2 = UsersStatistic.builder()
                .qnt(4000)
                .admins(10)
                .addedDate(LocalDateTime.now())
                .build();

        usersStatisticRepository.saveAll(List.of(usersStatistic, usersStatistic2));

        //When
        UsersStatistic result = usersStatisticRepository.retrieveTheNewestStats().orElseThrow();

        //Then
        assertEquals(usersStatistic2.getId(), result.getId());
        assertEquals(usersStatistic2.getAdmins(), result.getAdmins());
        assertEquals(usersStatistic2.getQnt(), result.getQnt());
    }

    @Test
    void findAll() {
        //Given
        UsersStatistic usersStatistic = UsersStatistic.builder()
                .qnt(3000)
                .admins(5)
                .addedDate(LocalDateTime.now())
                .build();

        UsersStatistic usersStatistic2 = UsersStatistic.builder()
                .qnt(4000)
                .admins(10)
                .addedDate(LocalDateTime.now())
                .build();

        usersStatisticRepository.saveAll(List.of(usersStatistic, usersStatistic2));

        //When
        List<UsersStatistic> resultList = usersStatisticRepository.findAll();

        //Then
        assertEquals(2, resultList.size());
        assertEquals(usersStatistic.getId(), resultList.get(0).getId());
        assertEquals(usersStatistic.getQnt(), resultList.get(0).getQnt());
        assertEquals(usersStatistic.getAdmins(), resultList.get(0).getAdmins());
        assertEquals(usersStatistic2.getId(), resultList.get(1).getId());
        assertEquals(usersStatistic2.getQnt(), resultList.get(1).getQnt());
        assertEquals(usersStatistic2.getAdmins(), resultList.get(1).getAdmins());
    }
}