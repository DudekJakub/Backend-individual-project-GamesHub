package com.gameshub.repository;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
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
    void findById() {
        //Given
        Long gameToFindId = 1L;

        //When
        Game game = repository.findById(gameToFindId).orElseThrow();

        //Then
        assertEquals(1L, game.getId());
    }

    @Test
    void findAll() {
        //When
        List<Game> gamesFromDatabase = repository.findAll();

        //Then
        assertTrue(gamesFromDatabase.size() > 0);
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