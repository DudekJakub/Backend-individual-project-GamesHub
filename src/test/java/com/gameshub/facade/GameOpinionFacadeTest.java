package com.gameshub.facade;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.*;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.repository.GameRepository;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameOpinionFacadeTest {

    @Autowired
    private GameOpinionFacade facade;

    @Autowired
    private GameOpinionRepository gameOpinionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;


    @Test
    @Transactional
    void createGameOpinion() throws GameOpinionLengthTooLongException, GameNotFoundException {
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

        GameOpinion newOpinion = GameOpinion.builder()
                .opinion("test_opinion")
                .gameName(gameForTest.getName())
                .userLogin(userForTest.getLoginName())
                .build();

        GameOpinion gameOpinion = gameOpinionRepository.save(newOpinion);
        gameOpinion.setGame(game);
        gameOpinion.setUser(user);
        GameOpinion savedOpinion = gameOpinionRepository.save(gameOpinion);

        GameOpinionDto opinionDto = GameOpinionDto.builder()
                .id(savedOpinion.getId())
                .opinion("test")
                .gameId(2L)
                .userId(user.getId())
                .build();

        //When
        var result = facade.createGameOpinion(opinionDto);

        //Then
        assertEquals(opinionDto.getOpinion(), result.getOpinion());
        assertEquals(opinionDto.getUserId(), result.getUserId());
        assertEquals(opinionDto.getGameId(), result.getGameId());
    }

    @Test
    @Transactional
    void updateGameOpinion() throws GameOpinionLengthTooLongException, UserNotFoundException, GameOpinionNotFoundException, GameOpinionUpdateTimeExpiredException, GameDataUpdateAccessDeniedException {
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


        GameOpinion newOpinion = GameOpinion.builder()
                .opinion("test_opinion")
                .game(game)
                .user(user)
                .gameName(gameForTest.getName())
                .userLogin(userForTest.getLoginName())
                .build();

        GameOpinion savedOpinion = gameOpinionRepository.save(newOpinion);

        //When
        facade.updateGameOpinion("hehe", user.getId(), savedOpinion.getId());

        //Then
        var updatedOpinion = gameOpinionRepository.findById(savedOpinion.getId()).orElseThrow();

        assertEquals("hehe", updatedOpinion.getOpinion());
    }

    @Test
    @Transactional
    void deleteGameOpinion() throws GameOpinionNotFoundException {
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


        GameOpinion newOpinion = GameOpinion.builder()
                .opinion("test_opinion")
                .game(game)
                .user(user)
                .gameName(gameForTest.getName())
                .userLogin(userForTest.getLoginName())
                .build();

        GameOpinion savedOpinion = gameOpinionRepository.save(newOpinion);

        int opinionRepoSize = gameOpinionRepository.findAll().size();

        //When
        facade.deleteGameOpinion(savedOpinion.getId());

        //Then
        assertEquals(0, gameOpinionRepository.findAll().size());
        assertEquals(0, opinionRepoSize - 1);
    }

    @Test
    @Transactional
    void fetchGameOpinionListForGivenGameId() throws GameNotFoundException {
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


        GameOpinion newOpinion = GameOpinion.builder()
                .opinion("test_opinion")
                .game(game)
                .user(user)
                .gameName(gameForTest.getName())
                .userLogin(userForTest.getLoginName())
                .build();

        gameOpinionRepository.save(newOpinion);

        //When
        var result = facade.fetchGameOpinionListForGivenGameId(2L);

        //Then
        assertEquals(1, result.size());
    }
}