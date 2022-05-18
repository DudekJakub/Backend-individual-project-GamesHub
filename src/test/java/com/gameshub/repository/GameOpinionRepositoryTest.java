package com.gameshub.repository;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameOpinionRepositoryTest {

    @Autowired
    private GameOpinionRepository gameOpinionRepository;

    private Game gameForTest = null;
    private User userForTest = null;

    @BeforeEach
    void setPreliminaryData() {
        userForTest = User.builder()
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
    @Transactional
    void saveEntity() {
        //Given
        int opinionsListSizeBeforePersistNewOpinion = gameOpinionRepository.findAll().size();

        GameOpinion newOpinion = GameOpinion.builder()
                .opinion("test_opinion")
                .gameName(gameForTest.getName())
                .userLogin(userForTest.getLoginName())
                .game(gameForTest)
                .user(userForTest)
                .build();

        //When
        gameOpinionRepository.save(newOpinion);

        //Then
        assertEquals(opinionsListSizeBeforePersistNewOpinion + 1, gameOpinionRepository.findAll().size());
    }
}
