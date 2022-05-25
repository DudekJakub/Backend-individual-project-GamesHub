package com.gameshub.facade;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameAlreadySubscribedException;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.GameNotSubscribedException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.GameRepository;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameFacadeTest {

    @Autowired
    private GameFacade gameFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    @Transactional
    void subscribeGame() throws UserNotFoundException, GameAlreadySubscribedException, GameNotFoundException {
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

        User savedUser =userRepository.save(userForTest);
        gameRepository.save(gameForTest);

        //When
        var result = gameFacade.subscribeGame(2L, savedUser.getId());

        //Then
        Game game = gameRepository.findById(2L).orElseThrow();
        User user = userRepository.findById(savedUser.getId()).orElseThrow();

        assertEquals(user.getObservedGames().size(), game.getObservers().size());
        assertTrue(user.getObservedGames().contains(game));
        assertTrue(game.getObservers().contains(user));
    }

    @Test
    void unsubscribeGame() throws UserNotFoundException, GameNotFoundException, GameNotSubscribedException, GameAlreadySubscribedException {
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

        User user = userRepository.save(userForTest);
        gameRepository.save(gameForTest);
        gameFacade.subscribeGame(2L, user.getId());

        //When
        gameFacade.unsubscribeGame(2L, user.getId());

        //Then
        User savedUser = userRepository.findById(user.getId()).orElseThrow();

        assertEquals(0, savedUser.getObservedGames().size());
    }

    @Test
    void markGameAsOwned() throws UserNotFoundException, GameNotFoundException {
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

        User user = userRepository.save(userForTest);
        gameRepository.save(gameForTest);

        //When
        gameFacade.markGameAsOwned(2L, user.getId());

        //Then
        User savedUser = userRepository.findById(user.getId()).orElseThrow();

        assertEquals(1, savedUser.getGamesOwned().size());
    }

    @Test
    void markGameAsWantedToOwn() throws UserNotFoundException, GameNotFoundException {
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

        User user = userRepository.save(userForTest);
        gameRepository.save(gameForTest);

        //When
        gameFacade.markGameAsWantedToOwn(2L, user.getId());

        //Then
        User savedUser = userRepository.findById(user.getId()).orElseThrow();

        assertEquals(1, savedUser.getGamesWantedToOwn().size());
    }

    @Test
    void fetchGameObservers() throws UserNotFoundException, GameAlreadySubscribedException, GameNotFoundException {
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

        User user = userRepository.save(userForTest);
        gameRepository.save(gameForTest);
        gameFacade.subscribeGame(2L, user.getId());

        //When
        var result = gameFacade.fetchGameObservers(2L);

        //Then
        assertEquals(1, result.size());
    }

    @Test
    void fetchGamesUserOwns() throws UserNotFoundException, GameNotFoundException {
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

        User user = userRepository.save(userForTest);
        gameRepository.save(gameForTest);
        gameFacade.markGameAsOwned(2L, user.getId());

        //When
        var result = gameFacade.fetchGamesUserOwns(user.getId());

        //Then
        assertEquals(1, result.size());
    }

    @Test
    void fetchGamesUserWantsToOwn() throws UserNotFoundException, GameNotFoundException {
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

        User user = userRepository.save(userForTest);
        gameRepository.save(gameForTest);
        gameFacade.markGameAsWantedToOwn(2L, user.getId());

        //When
        var result = gameFacade.fetchGamesUserWantsToOwn(user.getId());

        //Then
        assertEquals(1, result.size());
    }
}