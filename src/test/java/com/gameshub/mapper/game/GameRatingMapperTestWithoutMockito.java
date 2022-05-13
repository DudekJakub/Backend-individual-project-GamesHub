package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.game.GameRatingDto;
import com.gameshub.domain.user.User;
import com.gameshub.exceptions.GameNotFoundException;
import com.gameshub.exceptions.UserNotFoundException;
import com.gameshub.mapper.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameRatingMapperTestWithoutMockito {

    @Autowired
    private GameRatingMapper gameRatingMapper;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    void mapToGameRating() throws GameNotFoundException,
                                  UserNotFoundException {
        //Given
        GameRatingDto gameRatingDto = GameRatingDto.builder()
                .id(1L)
                .gameName("destiny-2")
                .rating(5)
                .gameId(32L)
                .userId(5L)
                .build();

        //When
        GameRating mappedResult = gameRatingMapper.mapToGameRating(gameRatingDto);

        //Then
        assertNotNull(mappedResult);
        assertNotNull(mappedResult.getPublicationDate());
        assertEquals("destiny-2", mappedResult.getGameName());
        assertEquals("destiny-2", mappedResult.getGame().getName());
        assertEquals("admin", mappedResult.getUser().getLoginName());
        assertEquals(5, mappedResult.getRating());
    }

    @Test
    void mapToGameRatingDto() throws GameNotFoundException,
                                     UserNotFoundException {
        //Given
        Game gameFromDatabase = gameMapper.mapToGameFromId(32L);
        User userFromDatabase = userMapper.mapToUserFromId(5L);

        GameRating gameRating = GameRating.builder()
                .id(1L)
                .gameName("destiny-2")
                .rating(5)
                .game(gameFromDatabase)
                .user(userFromDatabase)
                .build();

        //When
        GameRatingDto mappedResult = gameRatingMapper.mapToGameRatingDto(gameRating);

        //Then
        assertNotNull(mappedResult);
        assertNotNull(mappedResult.getPublicationDate());
        assertEquals("destiny-2", mappedResult.getGameName());
        assertEquals(32L, mappedResult.getGameId());
        assertEquals(5L, mappedResult.getUserId());
        assertEquals(5, mappedResult.getRating());
    }

}
