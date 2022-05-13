package com.gameshub.repository;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
import com.gameshub.exceptions.GameNotFoundException;
import com.gameshub.mapper.game.GameMapper;
import com.gameshub.rawg.client.RawgClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameRepositoryTest {

    @Autowired
    private GameRepository repository;

    @Autowired
    private RawgClient rawgClient;

    @Autowired
    private GameMapper gameMapper;

    @Test
    void retrieveGamesWhereNameIsLike() {
        //Given
        String givenName = "doom";

        //When
        List<Long> resultList = repository.retrieveGamesWhereNameIsLike(givenName);

        //Then
        assertEquals(6, resultList.size());
    }

    @Test
    void findById() throws GameNotFoundException {
        //Given
        Long gameToFindId = 2728L;

        //When
        Game game = repository.findById(gameToFindId).orElseThrow(GameNotFoundException::new);

        //Then
        assertEquals(2728L, game.getId());
        assertEquals("tom-clancys-the-division", game.getName());
    }

    @Test
    void findAll() {
        //When
        List<Game> gamesFromDatabase = repository.findAll();

        //Then
        assertTrue(gamesFromDatabase.size() > 0);
        assertEquals(4508, gamesFromDatabase.size());
    }

    @Test
    @Transactional
    void save() {
        //Given
        RawgGameDetailedDto gameFromRawgDatabase = rawgClient.getGameById(1L).orElseThrow();
        Game gameToPersist = gameMapper.mapToGame(gameFromRawgDatabase);

        int allGameSizeBeforeSave = repository.findAll().size();

        //When
        Game game = repository.save(gameToPersist);
        Long id = game.getId();

        //Then
        int allGameSizeAfterSave = repository.findAll().size();

        assertEquals(allGameSizeAfterSave, allGameSizeBeforeSave + 1);
        assertEquals(1L, id);
        assertEquals("D/Generation HD", game.getName());
    }
}