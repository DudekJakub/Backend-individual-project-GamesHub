package com.gameshub.repository;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.rawgGame.RawgGameDetailedDto;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.mapper.game.GameMapper;
import com.gameshub.client.RawgClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    void save() {
        //Given
        Game game = Game.builder()
                .id(1L)
                .name("test_name")
                .build();

        //When
        Game savedGame = gameRepository.save(game);

        //Then
        assertEquals(1L, savedGame.getId());
        assertEquals("test_name", savedGame.getName());
    }

    @Test
    void retrieveGamesWhereNameIsLike() {
        //Given
        Game game = Game.builder()
                .id(1L)
                .name("test_name")
                .build();

        Game game2 = Game.builder()
                .id(2L)
                .name("test_name")
                .build();

        Game game3 = Game.builder()
                .id(3L)
                .name("helloWorld")
                .build();

        gameRepository.saveAll(List.of(game, game2, game3));

        //When
        List<Long> resultList = gameRepository.retrieveGamesWhereNameIsLike("name");

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.containsAll(List.of(1L, 2L)));
    }

    @Test
    void findById()  {
        //Given
        Game game = Game.builder()
                .id(1L)
                .name("test_name")
                .build();

        Game savedGame = gameRepository.save(game);

        //When
        Game resultGame = gameRepository.findById(savedGame.getId()).orElseThrow();

        //Then
        assertEquals(savedGame.getId(), resultGame.getId());
        assertEquals(savedGame.getName(), resultGame.getName());
    }

    @Test
    void findAll() {
        //Given
        Game game = Game.builder()
                .id(1L)
                .name("test_name")
                .build();

        Game game2 = Game.builder()
                .id(2L)
                .name("test_name")
                .build();

        gameRepository.saveAll(List.of(game, game2));

        //When
        List<Game> resultList = gameRepository.findAll();

        //Then
        assertEquals(2, resultList.size());
    }


}