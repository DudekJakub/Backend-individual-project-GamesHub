package com.gameshub.validator;

import com.gameshub.exception.GameNotFoundException;
import com.gameshub.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameValidator.class);

    private final GameRepository gameRepository;

    public boolean checkByIdIfGameExistsInDatabase(final Long gameId, final String operationName) throws GameNotFoundException {
        boolean doesGameExistInDatabase = gameRepository.findById(gameId).isPresent();

        if (!doesGameExistInDatabase) {
            LOGGER.error(operationName + "Validation failed! Game not found!");
            throw new GameNotFoundException();
        }
        return true;
    }
}
