package com.gameshub;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.GameRepository;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

public class EqualsAndHashCodesTestSuite {

    @Test
    void userEqualsAndHashCodeContract() {
        //Given
        User userForTest1 = User.builder()
                .id(1L)
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.FULL_EMAIL_NOTIFICATION)
                .email("test_email")
                .password("test_password")
                .firstname("test_firstname")
                .lastname("test_lastname")
                .active(false)
                .verified(true)
                .build();

        User userForTest2 = User.builder()
                .id(1L)
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.FULL_EMAIL_NOTIFICATION)
                .email("test_email")
                .password("test_password")
                .firstname("test_firstname")
                .lastname("test_lastname")
                .active(false)
                .verified(true)
                .build();

        //When
        boolean areHashCodesEqual = userForTest1.hashCode() == userForTest2.hashCode();
        boolean areObjectsEqual = userForTest1.equals(userForTest2);

        //Then
        assertNotNull(userForTest1);
        assertNotNull(userForTest1);
        assertEquals(userForTest1.getId(), userForTest2.getId());
        assertEquals(userForTest1.getLoginName(), userForTest2.getLoginName());
        assertTrue(areHashCodesEqual);
        assertTrue(areObjectsEqual);
    }

    @Test
    void gameEqualsAndHashCodeContract() {
        //Given
        Game gameForTest1 = Game.builder()
                .id(1L)
                .name("test_game")
                .build();

        Game gameForTest2 = Game.builder()
                .id(1L)
                .name("test_game")
                .build();

        //When
        boolean areHashCodesEqual = gameForTest1.hashCode() == gameForTest2.hashCode();
        boolean areObjectsEqual = gameForTest1.equals(gameForTest2);

        //Then
        assertNotNull(gameForTest1);
        assertNotNull(gameForTest1);
        assertEquals(gameForTest1.getId(), gameForTest2.getId());
        assertTrue(areHashCodesEqual);
        assertTrue(areObjectsEqual);
    }

}
