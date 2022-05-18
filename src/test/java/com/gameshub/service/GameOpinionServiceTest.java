package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.mapper.game.GameOpinionMapper;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameOpinionServiceTest {

    @InjectMocks
    private GameOpinionService service;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameOpinionMapper gameOpinionMapper;

    @Mock
    private GameOpinionRepository gameOpinionRepository;


    private User userForTest = null;
    private Game gameForTest = null;

    @BeforeEach
    void setPreliminaryData() {
        userForTest = User.builder()
                .id(1L)
                .loginName("test_loginName")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.FULL_EMAIL_NOTIFICATION)
                .email("test_email")
                .firstname("test_firstname")
                .lastname("test_lastname")
                .active(true)
                .verified(true)
                .build();

        gameForTest = Game.builder()
                .id(1L)
                .name("test_game")
                .build();
    }


    @Test
    void createGameOpinion() throws UserNotFoundException, GameNotFoundException {
        //Given
        GameOpinionDto newOpinionDto = GameOpinionDto.builder()
                .id(1L)
                .opinion("test_opinion")
                .gameId(gameForTest.getId())
                .userId(userForTest.getId())
                .publicationDate(LocalDateTime.now())
                .build();

        GameOpinion mappedNewOpinion = GameOpinion.builder()
                .id(1L)
                .opinion("test_opinion")
                .game(gameForTest)
                .user(userForTest)
                .gameName(gameForTest.getName())
                .userLogin(userForTest.getLoginName())
                .build();

        when(gameRepository.findById(anyLong())).thenReturn(Optional.ofNullable(gameForTest));
        when(gameOpinionMapper.mapToGameOpinion(newOpinionDto)).thenReturn(mappedNewOpinion);
        when(gameOpinionRepository.save(mappedNewOpinion)).thenReturn(mappedNewOpinion);

        //When
        GameOpinion resultNewOpinion = service.createGameOpinion(newOpinionDto);

        //Then
        assertNotNull(resultNewOpinion);
        assertEquals(newOpinionDto.getId(), mappedNewOpinion.getId());
        assertEquals(newOpinionDto.getOpinion(), mappedNewOpinion.getOpinion());
        assertEquals(newOpinionDto.getGameId(), mappedNewOpinion.getGame().getId());
        assertEquals(newOpinionDto.getUserId(), mappedNewOpinion.getUser().getId());
        assertEquals(newOpinionDto.getPublicationDate().getMinute(), mappedNewOpinion.getPublicationDate().getMinute());
    }
}