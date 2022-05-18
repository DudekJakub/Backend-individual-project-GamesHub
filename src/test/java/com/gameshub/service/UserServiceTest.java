package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.user.User;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void loadUserByUsername() {
    }

    @Test
    void signUpUser() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserByLoginName() {
    }

    @Test
    void setUserNotificationStrategy() {
    }

    @Test
    void getUserObservedGames() throws UserNotFoundException {
        //Given
        Long userId = 5L;
        User userFromDatabase = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Set<Game> observedGames = userFromDatabase.getObservedGames();

        //When
        Set<Game> resultUserObservedGames = userService.getUserObservedGames(userId);

        //Then
        observedGames.forEach(game -> System.out.println("Games directly from User: \n " + game.getName() + "\n"));
        resultUserObservedGames.forEach(game -> System.out.println("Games from tested method: \n " + game.getName() + "\n"));

        assertEquals(observedGames.size(), resultUserObservedGames.size());
    }
}