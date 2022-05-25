package com.gameshub.repository;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameRatingRepositoryTest {

    @Autowired
    private GameRatingRepository gameRatingRepository;


    @Test
    void findById() {
        //Given
        GameRating newRating = GameRating.builder()
                .rating(5)
                .game(Game.builder()
                        .id(1L)
                        .name("test_gameName")
                        .build())
                .gameName("test_gameName")
                .build();

        GameRating savedRating = gameRatingRepository.save(newRating);

        //When
        GameRating resultRating = gameRatingRepository.findById(savedRating.getId()).orElseThrow();

        //Then
        assertEquals(savedRating.getId(), resultRating.getId());
        assertEquals(savedRating.getGame(), resultRating.getGame());
    }

    @Test
    void findAll() {
        //Given
        GameRating newRating = GameRating.builder()
                .rating(5)
                .game(Game.builder()
                        .id(1L)
                        .name("test_gameName")
                        .build())
                .gameName("test_gameName")
                .build();

        GameRating newRating2 = GameRating.builder()
                .rating(10)
                .game(Game.builder()
                        .id(2L)
                        .name("test_gameName2")
                        .build())
                .gameName("test_gameName2")
                .build();

        gameRatingRepository.saveAll(List.of(newRating, newRating2));

        //When
        List<GameRating> resultList = gameRatingRepository.findAll();

        //Then
        assertEquals(2, resultList.size());
        assertEquals(newRating.getId(), resultList.get(0).getId());
        assertEquals(newRating2.getId(), resultList.get(1).getId());
        assertEquals(newRating.getGame(), resultList.get(0).getGame());
        assertEquals(newRating2.getGame(), resultList.get(1).getGame());
    }
}