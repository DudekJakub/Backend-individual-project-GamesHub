package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameOpinionDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameOpinionMapperTestWithMockito {

    @InjectMocks
    private GameOpinionMapper gameOpinionMapper;

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
    void mapToGameOpinion() throws GameNotFoundException,
                                   UserNotFoundException {
        //Given
        GameOpinionDto gameOpinionDto = GameOpinionDto.builder()
                .id(1L)
                .gameName("destiny-2")
                .userLogin("admin")
                .opinion("Test_opinion")
                .gameId(32L)
                .userId(5L)
                .build();

        when(gameMapper.mapToGameFromId(32L)).thenReturn(game);
        when(userMapper.mapToUserFromId(5L)).thenReturn(user);

        //When
        GameOpinion mappedResult = gameOpinionMapper.mapToGameOpinion(gameOpinionDto);

        //Then
        verify(gameMapper, times(1)).mapToGameFromId(32L);
        verify(userMapper, times(1)).mapToUserFromId(5L);
        assertNotNull(mappedResult);
        assertNotNull(mappedResult.getPublicationDate());
        assertEquals("destiny-2", mappedResult.getGame().getName());
        assertEquals("destiny-2", mappedResult.getGameName());
        assertEquals("admin", mappedResult.getUser().getLoginName());
        assertEquals("admin", mappedResult.getUserLogin());
    }

    @Test
    void mapToGameOpinionDto() {
        //Given
        GameOpinion gameOpinion = GameOpinion.builder()
                .id(1L)
                .gameName("destiny-2")
                .userLogin("admin")
                .opinion("test_opinion")
                .game(game)
                .user(user)
                .build();

        //When
        GameOpinionDto mappedResult = gameOpinionMapper.mapToGameOpinionDto(gameOpinion);

        //Then
        assertNotNull(mappedResult);
        assertNotNull(mappedResult.getPublicationDate());
        assertEquals("destiny-2", mappedResult.getGameName());
        assertEquals("admin", mappedResult.getUserLogin());
        assertEquals("test_opinion", mappedResult.getOpinion());
        assertEquals(32L, mappedResult.getGameId());
        assertEquals(5L, mappedResult.getUserId());
    }
}