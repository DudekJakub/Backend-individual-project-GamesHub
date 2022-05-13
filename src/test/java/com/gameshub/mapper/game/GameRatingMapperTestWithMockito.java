package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.game.GameRatingDto;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exceptions.GameNotFoundException;
import com.gameshub.exceptions.UserNotFoundException;
import com.gameshub.mapper.user.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GameRatingMapperTestWithMockito {

    @InjectMocks
    private GameRatingMapper gameRatingMapper;

    @Mock
    private GameMapper gameMapper;

    @Mock
    private UserMapper userMapper;

    private Game game = null;
    private User user = null;

    @BeforeEach
    void setGameAndUser(TestInfo testInfo) {
        System.out.println("Test - " + testInfo.getDisplayName() + " - started.");

        game = Game.builder()
                .id(32L)
                .name("destiny-2")
                .build();

        user = User.builder()
                .id(5L)
                .loginName("admin")
                .appUserRole(AppUserRole.ADMIN)
                .email("jakubjavaprogrammer@gmail.com")
                .active(true)
                .verified(true)
                .firstname("admin")
                .lastname("admin")
                .build();
    }

    @AfterEach
    void setAfterEachMessage(TestInfo testInfo) {
        System.out.println("Test - " + testInfo.getDisplayName() + " - finished.");
    }

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

        when(gameMapper.mapToGameFromId(32L)).thenReturn(game);
        when(userMapper.mapToUserFromId(5L)).thenReturn(user);

        //When
        GameRating mappedResult = gameRatingMapper.mapToGameRating(gameRatingDto);

        //Then
        verify(gameMapper, times(1)).mapToGameFromId(32L);
        verify(userMapper, times(1)).mapToUserFromId(5L);
        assertNotNull(mappedResult);
        assertNotNull(mappedResult.getPublicationDate());
        assertEquals("destiny-2", mappedResult.getGameName());
        assertEquals("destiny-2", mappedResult.getGame().getName());
        assertEquals(32L, mappedResult.getGame().getId());
        assertEquals("admin", mappedResult.getUser().getLoginName());
        assertEquals(5L, mappedResult.getUser().getId());
        assertEquals(5, mappedResult.getRating());
    }

    @Test
    void mapToGameRatingDto() {
        //Given
        GameRating gameRating = GameRating.builder()
                .id(1L)
                .gameName("destiny-2")
                .rating(5)
                .game(game)
                .user(user)
                .build();

        //When
        GameRatingDto mappedResult = gameRatingMapper.mapToGameRatingDto(gameRating);

        //Then
        assertNotNull(mappedResult);
        assertNotNull(mappedResult.getPublicationDate());
        assertEquals("destiny-2", mappedResult.getGameName());
        assertEquals(5, mappedResult.getRating());
        assertEquals(32L, mappedResult.getGameId());
        assertEquals(5L, mappedResult.getUserId());
    }
}
