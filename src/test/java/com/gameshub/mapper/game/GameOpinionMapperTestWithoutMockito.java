package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.domain.user.User;
import com.gameshub.exceptions.GameNotFoundException;
import com.gameshub.exceptions.UserNotFoundException;
import com.gameshub.mapper.user.UserMapper;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameOpinionMapperTestWithoutMockito {

    @Autowired
    private GameOpinionMapper gameOpinionMapper;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    void mapToGameOpinion() throws GameNotFoundException,
                                   UserNotFoundException {
        //Given
        GameOpinionDto gameOpinionDto = GameOpinionDto.builder()
                .id(1L)
                .gameName("destiny-2")
                .userLogin("admin")
                .opinion("test_opinion")
                .gameId(32L)
                .userId(5L)
                .build();

        //When
        GameOpinion mappedResult = gameOpinionMapper.mapToGameOpinion(gameOpinionDto);

        //Then
        assertNotNull(mappedResult);
        assertNotNull(mappedResult.getPublicationDate());
        assertEquals(1L, mappedResult.getId());
        assertEquals("destiny-2", mappedResult.getGameName());
        assertEquals("test_opinion", mappedResult.getOpinion());
        assertEquals("admin", mappedResult.getUserLogin());
        assertEquals(32L, mappedResult.getGame().getId());
        assertEquals(5L, mappedResult.getUser().getId());
    }

    @Test
    void mapToGameOpinionDto() throws GameNotFoundException,
                                      UserNotFoundException {
        //Given
        Game gameFromDatabase = gameMapper.mapToGameFromId(32L);
        User userFromDatabase = userMapper.mapToUserFromId(5L);

        GameOpinion gameOpinion = GameOpinion.builder()
                .id(1L)
                .gameName("destiny-2")
                .userLogin("admin")
                .opinion("test_opinion")
                .game(gameFromDatabase)
                .user(userFromDatabase)
                .build();

        //When
        GameOpinionDto mappedResult = gameOpinionMapper.mapToGameOpinionDto(gameOpinion);

        //Then
        assertNotNull(mappedResult);
        assertNotNull(mappedResult.getPublicationDate());
        assertEquals(1L, mappedResult.getId());
        assertEquals("destiny-2", mappedResult.getGameName());
        assertEquals("admin", mappedResult.getUserLogin());
        assertEquals(32L, mappedResult.getGameId());
        assertEquals(5L, mappedResult.getUserId());
    }
}
