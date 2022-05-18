package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.rawgGame.RawgGameDetailedDto;
import com.gameshub.rawg.client.RawgClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameMapperTest {

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private RawgClient rawgClient;

    @Test
    void mapToGame() {
        //Given
        RawgGameDetailedDto gameToMap = RawgGameDetailedDto.builder()
                .id(1L)
                .name("test_game")
                .description("test_game")
                .metacritic(90.0)
                .released(LocalDate.now())
                .tba(false)
                .officialWebsiteURL("https://www.test-game.com")
                .build();

        //When
        Game game = gameMapper.mapToGame(gameToMap);

        //Then
        assertEquals(1L, game.getId());
        assertEquals("test_game", game.getName());
        assertEquals(0, game.getGameOpinions().size());
    }

    @Test
    void mapToGameFromRawgClient() {
        //Given
        RawgGameDetailedDto gameToMap = rawgClient.getGameById(1L).orElseThrow();
        Long rawgGameId = gameToMap.getId();
        String rawgGameName = gameToMap.getName();

        //When
        Game game = gameMapper.mapToGame(gameToMap);

        //Then
        assertEquals(rawgGameId, game.getId());
        assertEquals(rawgGameName, game.getName());
        assertEquals(0, game.getGameOpinions().size());
    }
}