package com.gameshub.validator;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.exception.GameOpinionNotFoundException;
import com.gameshub.exception.GameOpinionUpdateTimeExpiredException;
import com.gameshub.exception.GameOpinionWithProfanitiesException;
import com.gameshub.repository.GameOpinionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameOpinionValidatorTest {

    private final static String TEST = "[TESTING OPERATION] ";

    @Autowired
    private GameOpinionValidator gameOpinionValidator;

    @Autowired
    private GameOpinionRepository gameOpinionRepository;

    @Test
    void validateOpinionForCurses() {
        //Given
        String opinion = "fuck";

        //When
        GameOpinionWithProfanitiesException thrown = Assertions.assertThrows(GameOpinionWithProfanitiesException.class, () ->
                gameOpinionValidator.validateOpinionForCurses(opinion, TEST));

        //Then
        assertEquals("Given opinion contains profanities!", thrown.getMessage());
    }

    @Test
    void validateOpinionForPublicationDate() throws GameOpinionNotFoundException {
        //Given
        GameOpinion opinionToTest = gameOpinionRepository.findById(37L).orElseThrow(GameOpinionNotFoundException::new);

        //When
        GameOpinionUpdateTimeExpiredException thrown = Assertions.assertThrows(GameOpinionUpdateTimeExpiredException.class, () ->
            gameOpinionValidator.validateOpinionForPublicationDate(opinionToTest, TEST));

        //THen
        assertEquals("Opinion is too old to update!", thrown.getMessage());
    }
}