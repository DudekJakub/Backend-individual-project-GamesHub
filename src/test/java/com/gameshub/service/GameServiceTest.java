package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
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

import java.util.List;

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
    }

    @Test
    void unsubscribeGame() {
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
        assertFalse(gameForTest.getUsersWantedToOwnThisGame().contains(userForTest));
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
        assertTrue(gameForTest.getUsersWantedToOwnThisGame().contains(userForTest));
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
        assertTrue(gameForTest.getUsersWantedToOwnThisGame().contains(userForTest));
        assertTrue(userForTest.getGamesWantedToOwn().contains(gameForTest));
        assertFalse(gameForTest.getUsersOwnedThisGame().contains(userForTest));
        assertFalse(userForTest.getGamesOwned().contains(gameForTest));
    }

    @Test
    void getAllGameObservers() {
    }

    @Test
    void getThreeLatestGameOpinion() throws InterruptedException {
        //Given
        gameForTest.getGameOpinions().add(new GameOpinion(1L, "test_game1", "test_userLogin1", "test_opinion1", gameForTest, userForTest));
        Thread.sleep(1000);
        gameForTest.getGameOpinions().add(new GameOpinion(2L, "test_game2", "test_userLogin2", "test_opinion2", gameForTest, userForTest));
        Thread.sleep(1000);
        gameForTest.getGameOpinions().add(new GameOpinion(3L, "test_game3", "test_userLogin3", "test_opinion3", gameForTest, userForTest));
        Thread.sleep(1000);
        gameForTest.getGameOpinions().add(new GameOpinion(4L, "test_game4", "test_userLogin4", "test_opinion4", gameForTest, userForTest));

        int opinionsSize = gameForTest.getGameOpinions().size();
        GameOpinion oldestNotResultOpinion = gameForTest.getGameOpinions().get(opinionsSize-4);
        GameOpinion oldestResultOpinion = gameForTest.getGameOpinions().get(opinionsSize-3);
        GameOpinion middleResultOpinion = gameForTest.getGameOpinions().get(opinionsSize-2);
        GameOpinion earliestResultOpinion = gameForTest.getGameOpinions().get(opinionsSize-1);

        //When
        List<GameOpinion> threeLatestOpinions = gameService.getThreeLatestGameOpinions(gameForTest);

        //Then
        threeLatestOpinions.forEach(gameOpinion -> {
            System.out.println("\n" + gameOpinion.getOpinion());
            System.out.println(gameOpinion.getPublicationDate());
        });

        assertEquals(3, threeLatestOpinions.size());
        assertEquals(oldestResultOpinion.getOpinion(), threeLatestOpinions.get(2).getOpinion());
        assertEquals(middleResultOpinion.getOpinion(), threeLatestOpinions.get(1).getOpinion());
        assertEquals(earliestResultOpinion.getOpinion(), threeLatestOpinions.get(0).getOpinion());
        assertFalse(threeLatestOpinions.contains(oldestNotResultOpinion));
    }
}