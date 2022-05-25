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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GameOpinionRepositoryTest {

    @Autowired
    private GameOpinionRepository gameOpinionRepository;

    @Test
    @Transactional
    void save() {
        //Given
        GameOpinion newOpinion = GameOpinion.builder()
                .opinion("test_opinion")
                .game(Game.builder()
                        .id(1L)
                        .name("test_game_name")
                        .build())
                .gameName("test_game_name")
                .userLogin("test_userLogin")
                .build();

        //When
        GameOpinion savedOpinion = gameOpinionRepository.save(newOpinion);

        //Then
        assertEquals(newOpinion.getId(), savedOpinion.getId());
        assertEquals(newOpinion.getOpinion(), savedOpinion.getOpinion());
        assertEquals(newOpinion.getGame(), savedOpinion.getGame());
    }

    @Test
    void findById()  {
        //Given
        GameOpinion newOpinion = GameOpinion.builder()
                .opinion("test_opinion")
                .game(Game.builder()
                        .id(1L)
                        .name("test_game_name")
                        .build())
                .gameName("test_game_name")
                .userLogin("test_userLogin")
                .build();

        GameOpinion savedOpinion = gameOpinionRepository.save(newOpinion);

        //When
        GameOpinion resultGameOpinion = gameOpinionRepository.findById(savedOpinion.getId()).orElseThrow();

        //Then
        assertEquals(savedOpinion.getId(), resultGameOpinion.getId());
        assertEquals(savedOpinion.getGame(), resultGameOpinion.getGame());
    }

    @Test
    void findAll() {
        //Given
        GameOpinion newOpinion = GameOpinion.builder()
                .opinion("test_opinion")
                .game(Game.builder()
                        .id(1L)
                        .name("test_game_name")
                        .build())
                .gameName("test_game_name")
                .userLogin("test_userLogin")
                .build();

        GameOpinion newOpinion2 = GameOpinion.builder()
                .opinion("test_opinion2")
                .game(Game.builder()
                        .id(2L)
                        .name("test_game_name2")
                        .build())
                .gameName("test_game_name2")
                .userLogin("test_userLogin2")
                .build();

        gameOpinionRepository.saveAll(List.of(newOpinion, newOpinion2));

        //When
        List<GameOpinion> resultList = gameOpinionRepository.findAll();

        //Then
        assertEquals(2, resultList.size());
    }
}
