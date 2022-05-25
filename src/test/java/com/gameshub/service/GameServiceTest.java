package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.repository.GameRepository;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    private Game gameForTest;
    private User userForTest;

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
    void subscribeGame() {
        //Given
        when(gameRepository.save(gameForTest)).thenReturn(gameForTest);
        when(userRepository.save(userForTest)).thenReturn(userForTest);

        //When
        gameService.subscribeGame(gameForTest, userForTest);

        //Then
        assertTrue(userForTest.getObservedGames().contains(gameForTest));
        assertTrue(gameForTest.getObservers().contains(userForTest));
    }

    @Test
    void unsubscribeGame() {
        //Given
        when(gameRepository.save(gameForTest)).thenReturn(gameForTest);
        when(userRepository.save(userForTest)).thenReturn(userForTest);

        //When
        gameService.unsubscribeGame(gameForTest, userForTest);

        //Then
        assertFalse(userForTest.getObservedGames().contains(gameForTest));
        assertFalse(gameForTest.getObservers().contains(userForTest));
    }

    @Test
    void markGameAsOwned() {
        //Given
        when(gameRepository.save(gameForTest)).thenReturn(gameForTest);
        when(userRepository.save(userForTest)).thenReturn(userForTest);

        // When
        gameService.markGameAsOwned(gameForTest, userForTest);

        //Then
        assertTrue(gameForTest.getUsersOwnedThisGame().contains(userForTest));
        assertTrue(userForTest.getGamesOwned().contains(gameForTest));

    }

    @Test
    void markGameAsOwnedAfterMarkedAsWantedToOwn() {
        //Given
        when(gameRepository.save(gameForTest)).thenReturn(gameForTest);
        when(userRepository.save(userForTest)).thenReturn(userForTest);

        gameService.markGameAsWantedToOwn(gameForTest, userForTest);

        //When
        gameService.markGameAsOwned(gameForTest, userForTest);

        //Then
        assertTrue(gameForTest.getUsersOwnedThisGame().contains(userForTest));
        assertTrue(userForTest.getGamesOwned().contains(gameForTest));
        assertFalse(gameForTest.getUsersWantedThisGame().contains(userForTest));
        assertFalse(userForTest.getGamesWantedToOwn().contains(gameForTest));
    }

    @Test
    void markGameAsWantedToOwn() {
        //Given
        when(gameRepository.save(gameForTest)).thenReturn(gameForTest);
        when(userRepository.save(userForTest)).thenReturn(userForTest);

        //When
        gameService.markGameAsWantedToOwn(gameForTest, userForTest);

        //Then
        assertTrue(gameForTest.getUsersWantedThisGame().contains(userForTest));
        assertTrue(userForTest.getGamesWantedToOwn().contains(gameForTest));
    }

    @Test
    void markGameAsWantedToOwnAfterMarkedAsOwned() {
        when(gameRepository.save(gameForTest)).thenReturn(gameForTest);
        when(userRepository.save(userForTest)).thenReturn(userForTest);

        gameService.markGameAsOwned(gameForTest, userForTest);

        //When
        gameService.markGameAsWantedToOwn(gameForTest, userForTest);

        //Then
        assertTrue(gameForTest.getUsersWantedThisGame().contains(userForTest));
        assertTrue(userForTest.getGamesWantedToOwn().contains(gameForTest));
        assertFalse(gameForTest.getUsersOwnedThisGame().contains(userForTest));
        assertFalse(userForTest.getGamesOwned().contains(gameForTest));
    }

    @Test
    void getGameObservers() {
        //Given
        gameService.subscribeGame(gameForTest, userForTest);

        //When
        Set<User> gameObservers = gameService.getGameObservers(gameForTest);

        //Then
        List<User> gameObserversList = new ArrayList<>(gameObservers);

        assertTrue(gameObserversList.size() > 0);
        assertTrue(userForTest.getObservedGames().contains(gameForTest));
        assertEquals(gameObserversList.get(0).getLoginName(), userForTest.getLoginName());
        assertEquals(gameObserversList.get(0).getFirstname(), userForTest.getFirstname());
        assertEquals(gameObserversList.get(0).getLastname(), userForTest.getLastname());
    }

    @Test
    void getGamesUserOwns() {
        //Given
        gameService.markGameAsOwned(gameForTest, userForTest);

        //When
        List<Game> gamesUserOwns = new ArrayList<>(gameService.getGamesUserOwns(userForTest));

        System.out.println(gamesUserOwns.size());

        //Then
        assertTrue(gamesUserOwns.size() > 0);
        assertTrue(gamesUserOwns.contains(gameForTest));
    }

    @Test
    void getGamesUserWantsToOwn() {
        //Given
        gameService.markGameAsWantedToOwn(gameForTest, userForTest);

        //When
        List<Game> gamesUserWantsToOwn = new ArrayList<>(gameService.getGamesUserWantsToOwn(userForTest));

        //Then
        assertTrue(gamesUserWantsToOwn.size() > 0);
        assertTrue(gamesUserWantsToOwn.contains(gameForTest));
    }
}