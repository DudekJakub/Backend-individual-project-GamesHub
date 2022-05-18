package com.gameshub;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.GameRepository;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EqualsAndHashCodesTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    void userEqualsAndHashCodeContract() throws UserNotFoundException {
        //Given
        User admin1_1 = userRepository.findById(5L).orElseThrow(UserNotFoundException::new);
        User admin1_2 = userRepository.findAll()
                .stream()
                .filter(user -> user.getLoginName().equals("admin"))
                .findAny()
                .orElseThrow(UserNotFoundException::new);

        //When
        boolean areHashCodesEqual = admin1_1.hashCode() == admin1_2.hashCode();
        boolean areObjectsEqual = admin1_1.equals(admin1_2);

        //Then
        assertNotNull(admin1_1);
        assertNotNull(admin1_2);
        assertEquals(admin1_1.getId(), admin1_2.getId());
        assertEquals(admin1_1.getLoginName(), admin1_2.getLoginName());
        assertTrue(areHashCodesEqual);
        assertTrue(areObjectsEqual);
    }

    @Test
    void gameEqualsAndHashCodeContract() throws GameNotFoundException {
        //Given
        Game game1_1 = gameRepository.findById(23598L).orElseThrow(GameNotFoundException::new);
        Game game1_2 = gameRepository.findAll()
                .stream()
                .filter(game -> game.getId() == 23598L)
                .findAny()
                .orElseThrow(GameNotFoundException::new);

        //When
        boolean areHashCodesEqual = game1_1.hashCode() == game1_2.hashCode();
        boolean areObjectsEqual = game1_1.equals(game1_2);

        //Then
        assertNotNull(game1_1);
        assertNotNull(game1_2);
        assertEquals(game1_1.getId(), game1_2.getId());
        assertTrue(areHashCodesEqual);
        assertTrue(areObjectsEqual);
    }

}
