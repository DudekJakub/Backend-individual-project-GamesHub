package com.gameshub.facade;

import com.gameshub.domain.game.*;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.*;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.repository.GameRatingRepository;
import com.gameshub.repository.GameRepository;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameRatingFacadeTest {

    @Autowired
    private GameRatingFacade facade;

    @Autowired
    private GameRatingRepository gameRatingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    void createGameRating() throws UserNotFoundException, GameAlreadyRatedByUserException, GameRatingOutOfRangeException, GameNotFoundException {
        User userForTest = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        Game gameForTest = Game.builder()
                .id(2L)
                .name("test_game")
                .build();

        gameRepository.save(gameForTest);
        User user = userRepository.save(userForTest);

        GameRating newRating = GameRating.builder()
                .rating(5.0)
                .gameName(gameForTest.getName())
                .build();

        GameRating gameRating = gameRatingRepository.save(newRating);

        GameRatingDto ratingDto = GameRatingDto.builder()
                .id(gameRating.getId())
                .rating(5.0)
                .gameId(2L)
                .userId(user.getId())
                .build();

        //When
        var result = facade.createGameRating(ratingDto);

        //Then
        assertEquals(ratingDto.getRating(), result.getRating());
        assertEquals(ratingDto.getUserId(), result.getUserId());
        assertEquals(ratingDto.getGameId(), result.getGameId());
    }

    @Test
    void updateGameRating() throws UserNotFoundException, GameRatingNotFoundException, GameDataUpdateAccessDeniedException, GameRatingOutOfRangeException {
        User userForTest = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        Game gameForTest = Game.builder()
                .id(2L)
                .name("test_game")
                .build();

        Game game = gameRepository.save(gameForTest);
        User user = userRepository.save(userForTest);

        GameRating rating = GameRating.builder()
                .rating(5.0)
                .game(game)
                .user(user)
                .gameName(gameForTest.getName())
                .build();

        GameRating gameRating = gameRatingRepository.save(rating);

        //When
        facade.updateGameRating(3.0, user.getId(), rating.getId());

        //Then
        var result = gameRatingRepository.findById(gameRating.getId()).orElseThrow();

        assertEquals(3.0, result.getRating());
    }

    @Test
    void deleteGameRating() throws GameRatingNotFoundException {
        //Given
        User userForTest = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        Game gameForTest = Game.builder()
                .id(2L)
                .name("test_game")
                .build();

        Game game = gameRepository.save(gameForTest);
        User user = userRepository.save(userForTest);

        GameRating rating = GameRating.builder()
                .rating(5.0)
                .game(game)
                .user(user)
                .gameName(gameForTest.getName())
                .build();

        GameRating gameRating = gameRatingRepository.save(rating);

        int opinionRepoSize = gameRatingRepository.findAll().size();

        //When
        facade.deleteGameRating(gameRating.getId());

        //Then
        assertEquals(0, gameRatingRepository.findAll().size());
        assertEquals(0, opinionRepoSize - 1);
    }

    @Test
    void fetchGameRatingListForGivenGameId() throws GameNotFoundException {
        User userForTest = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        Game gameForTest = Game.builder()
                .id(2L)
                .name("test_game")
                .build();

        Game game = gameRepository.save(gameForTest);
        User user = userRepository.save(userForTest);

        GameRating rating = GameRating.builder()
                .rating(5.0)
                .game(game)
                .user(user)
                .gameName(gameForTest.getName())
                .build();

        gameRatingRepository.save(rating);

        //When
        var result = facade.fetchGameRatingListForGivenGameId(2L);

        //Then
        assertEquals(1, result.size());
    }
}