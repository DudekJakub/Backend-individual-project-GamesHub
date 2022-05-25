package com.gameshub.facade;

import com.gameshub.domain.book.Book;
import com.gameshub.domain.game.*;
import com.gameshub.domain.user.*;
import com.gameshub.exception.AccessDeniedException;
import com.gameshub.exception.UserEmailAlreadyExistsInDatabaseException;
import com.gameshub.exception.UserLoginNameAlreadyExistsInDatabaseException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserFacadeTest {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameOpinionRepository gameOpinionRepository;

    @Autowired
    private GameRatingRepository gameRatingRepository;

    private User userForTest;

    @BeforeEach
    void setSettings() {
        userForTest = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();
    }

    @Test
    @Transactional
    void signUpUser() throws UserEmailAlreadyExistsInDatabaseException, UserLoginNameAlreadyExistsInDatabaseException {
        //Given //When
        userRepository.save(userForTest);

        User userForTest2 = User.builder()
                .loginName("testing")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("jakub.dudek94@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        userFacade.signUpUser(userForTest2);

        //Then
        User userFromDatabase = userRepository.findByLoginName("testing").orElseThrow();

        assertNotNull(userFromDatabase);
        assertEquals(userForTest2.getAppUserRole(), userFromDatabase.getAppUserRole());
        assertEquals(userForTest2.getLoginName(), userFromDatabase.getLoginName());
        assertEquals(userForTest2.getNotificationStrategy(), userFromDatabase.getNotificationStrategy());
        assertEquals(userForTest2.getLastname(), userFromDatabase.getLastname());
        assertEquals(userForTest2.getFirstname(), userFromDatabase.getFirstname());
    }

    @Test
    @Transactional
    void setUserNotificationStrategy() throws UserNotFoundException {
        //Given
        User savedUser = userRepository.save(userForTest);

        //When
        String result = userFacade.setUserNotificationStrategy(savedUser.getId(), "app");

        //Then
        User resultUser = userRepository.findById(savedUser.getId()).orElseThrow();

        assertNotNull(result);
        assertNotNull(savedUser);
        assertEquals(AppUserNotificationStrategy.INSIDE_APP_NOTIFICATION, resultUser.getNotificationStrategy());
    }

    @Test
    @Transactional
    void fetchUserMemorizedBooks() throws UserNotFoundException {
        //Given
        userRepository.save(userForTest);

        Book bookForTest = Book.builder()
                .author("author")
                .googleId("googleId")
                .title("title")
                .build();

        userForTest.getBooksMemorized().add(bookForTest);
        bookForTest.getUsers().add(userForTest);

        User savedUser = userRepository.findById(userForTest.getId()).orElseThrow();

        //When
        var result = userFacade.fetchUserMemorizedBooks(savedUser.getId());

        //Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, savedUser.getBooksMemorized().size());
    }

    @Test
    @Transactional
    void fetchUserObservedGames() throws UserNotFoundException {
        //Given
        userRepository.save(userForTest);

        Game gameForTest3 = Game.builder()
                .id(20000L)
                .name("test_game")
                .build();

        gameRepository.save(gameForTest3);

        userForTest.getObservedGames().add(gameForTest3);
        User savedUser = userRepository.save(userForTest);

        //When
        userFacade.fetchUserObservedGames(savedUser.getId());

        //Then
        assertTrue(savedUser.getObservedGames().contains(gameForTest3));
        assertEquals(1, savedUser.getObservedGames().size());
    }

    @Test
    @Transactional
    void fetchUserGamesOpinions() throws UserNotFoundException {
        //Given
        Game gameForTest2 = Game.builder()
                .id(30000L)
                .name("test_game")
                .build();

        User userForTest2 = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        GameOpinion gameOpinion = GameOpinion.builder()
                .opinion("test_opinion")
                .game(gameForTest2)
                .user(userForTest2)
                .gameName(gameForTest2.getName())
                .userLogin(userForTest2.getLoginName())
                .build();

        userForTest2.getGameOpinions().add(gameOpinion);

        gameOpinionRepository.save(gameOpinion);

        //When
        List<GameOpinionDto> resultList = userFacade.fetchUserGamesOpinions(userForTest2.getId());

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(gameOpinion.getOpinion(), resultList.get(0).getOpinion());
        assertEquals(userForTest2.getGameOpinions().size(), resultList.size());
    }

    @Test
    @Transactional
    void fetchUserGamesRatings() throws UserNotFoundException {
        //Given
        Game gameForTest2 = Game.builder()
                .id(40000L)
                .name("test_game")
                .build();

        User userForTest2 = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        GameRating gameRating = GameRating.builder()
                .rating(5.0)
                .game(gameForTest2)
                .user(userForTest2)
                .gameName(gameForTest2.getName())
                .user(userForTest2)
                .build();

        userForTest2.getGameRatings().add(gameRating);

        gameRatingRepository.save(gameRating);

        //When
        List<GameRatingDto> resultList = userFacade.fetchUserGamesRatings(userForTest2.getId());

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(gameRating.getRating(), resultList.get(0).getRating());
    }

    @Test
    @Transactional
    void fetchUsersListForRegularUser() {
        //Given
        User userForTest2 = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        User savedUser = userRepository.save(userForTest2);

        //When
        List<UserOpenDto> resultList = userFacade.fetchUsersListForRegularUser();

        //Then
        assertEquals(1, resultList.size());
        assertEquals(savedUser.getLastname(), resultList.get(0).getLastname());
    }

    @Test
    @Transactional
    void fetchUsersListForAdmin() {
        //Given //When
        User userForTest2 = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        User savedUser = userRepository.save(userForTest2);

        List<UserCloseDto> resultList = userFacade.fetchUsersListForAdmin();

        //Then
        assertEquals(1, resultList.size());
        assertEquals(savedUser.getAppUserRole(), resultList.get(0).getAppUserRole());
        assertEquals(savedUser.getLoginName(), resultList.get(0).getLoginName());
    }

    @Test
    @Transactional
    void retrieveUserInfoForRegularUser() throws UserNotFoundException {
        //Given
        User userForTest2 = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .email("kek@gmail.com")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        User savedUser = userRepository.save(userForTest2);

        //When
        var result = userFacade.retrieveUserInfoForRegularUser(savedUser.getId());

        //Then
        assertEquals(savedUser.getFirstname(), result.getFirstname());
        assertEquals(savedUser.getLastname(), result.getLastname());
        assertEquals(savedUser.getEmail(), result.getEmail());
    }
}