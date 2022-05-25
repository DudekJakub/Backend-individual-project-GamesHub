package com.gameshub.service;

import com.gameshub.domain.game.*;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.mapper.game.GameRatingMapper;
import com.gameshub.repository.GameRatingRepository;
import com.gameshub.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameRatingServiceTest {

    @InjectMocks
    private GameRatingService service;

    @Mock
    private GameRatingRepository gameRatingRepository;

    @Mock
    private GameRatingMapper gameRatingMapper;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ApplicationContext appContext;

    private User userForTest;
    private Game gameForTest;

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
    void createGameRating() throws UserNotFoundException, GameNotFoundException {
        //Given
        GameRatingDto newRatingDto = GameRatingDto.builder()
                .id(1L)
                .rating(5.0)
                .gameId(gameForTest.getId())
                .userId(userForTest.getId())
                .publicationDate(LocalDateTime.now())
                .build();

        GameRating mappedNewRating = GameRating.builder()
                .id(1L)
                .rating(5.0)
                .game(gameForTest)
                .user(userForTest)
                .gameName(gameForTest.getName())
                .user(userForTest)
                .build();

        when(gameRepository.findById(anyLong())).thenReturn(Optional.ofNullable(gameForTest));
        when(gameRatingMapper.mapToGameRating(newRatingDto)).thenReturn(mappedNewRating);
        when(gameRatingRepository.save(mappedNewRating)).thenReturn(mappedNewRating);

        //When
        GameRating resultNewRating = service.createGameRating(newRatingDto);

        //Then
        assertNotNull(resultNewRating);
        assertEquals(newRatingDto.getId(), resultNewRating.getId());
        assertEquals(newRatingDto.getRating(), resultNewRating.getRating());
        assertEquals(newRatingDto.getGameId(), resultNewRating.getGame().getId());
        assertEquals(newRatingDto.getUserId(), resultNewRating.getUser().getId());
        assertEquals(newRatingDto.getPublicationDate().getMinute(), resultNewRating.getPublicationDate().getMinute());
    }

    @Test
    void updateGameRating() {
        //Given
        double updatedRatingValue = 9.0;
        GameRating toUpdate = GameRating.builder()
                .id(1L)
                .rating(5.0)
                .game(gameForTest)
                .user(userForTest)
                .gameName(gameForTest.getName())
                .user(userForTest)
                .build();

        when(gameRatingRepository.save(toUpdate)).thenReturn(toUpdate);

        //When
        GameRating updatedRating = service.updateGameRating(updatedRatingValue, toUpdate);

        //Then
        assertNotNull(updatedRating);
        assertEquals(toUpdate.getId(), updatedRating.getId());
        assertEquals(toUpdate.getRating(), updatedRating.getRating());
        assertEquals(toUpdate.getGame().getId(), updatedRating.getGame().getId());
        assertEquals(toUpdate.getUser().getId(), updatedRating.getUser().getId());
        assertEquals(toUpdate.getPublicationDate().getMinute(), updatedRating.getPublicationDate().getMinute());
    }

    @Test
    void getGameRatingList() {
        //Given
        GameRating toFind = GameRating.builder()
                .id(1L)
                .rating(5.0)
                .game(gameForTest)
                .user(userForTest)
                .gameName(gameForTest.getName())
                .user(userForTest)
                .build();

        gameForTest.getGameRatings().add(toFind);

        when(gameRatingRepository.findAll()).thenReturn(List.of(toFind));

        //When
        List<GameRating> resultList = service.getGameRatingList(gameForTest.getId());

        //Then
        assertTrue(resultList.size() > 0);
        assertEquals(toFind.getGame(), resultList.get(0).getGame());
        assertEquals(toFind.getId(), resultList.get(0).getId());
    }
}