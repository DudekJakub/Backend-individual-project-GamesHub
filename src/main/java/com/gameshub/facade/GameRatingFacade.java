package com.gameshub.facade;

import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.game.GameRatingDto;
import com.gameshub.exception.*;
import com.gameshub.mapper.game.GameRatingMapper;
import com.gameshub.repository.GameRatingRepository;
import com.gameshub.service.GameRatingService;
import com.gameshub.validator.GameRatingValidator;
import com.gameshub.validator.GameValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gameshub.facade.OperationName.*;

@Component
@RequiredArgsConstructor
public class GameRatingFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRatingFacade.class);

    private final GameRatingRepository gameRatingRepository;
    private final GameRatingService gameRatingService;
    private final GameRatingMapper gameRatingMapper;

    private final GameValidator gameValidator;
    private final GameRatingValidator gameRatingValidator;

    public GameRatingDto createGameRating(final GameRatingDto gameRatingDto) throws GameNotFoundException, UserNotFoundException,
                                                                                    GameRatingOutOfRangeException, GameAlreadyRatedByUserException {
        Long userId = gameRatingDto.getUserId();
        Long gameId = gameRatingDto.getGameId();
        double newRatingValue = gameRatingDto.getRating();

        gameRatingValidator.validateNewRatingPresence(gameId, userId, CREATE_GAME_RATING);
        gameRatingValidator.validateNewRatingRange(newRatingValue, CREATE_GAME_RATING);
        GameRating newRating = gameRatingService.createGameRating(gameRatingDto);

        return gameRatingMapper.mapToGameRatingDto(newRating);
    }

    public GameRatingDto updateGameRating(final double updatedRatingValue, final Long userId, final Long ratingId) throws UserNotFoundException, GameDataUpdateAccessDeniedException,
                                                                                                                          GameRatingNotFoundException, GameRatingOutOfRangeException {
        GameRating gameRatingToUpdate = gameRatingRepository.findById(ratingId).orElseThrow(GameRatingNotFoundException::new);

        LOGGER.info("Updating rating with ID: " + ratingId);
        gameRatingValidator.validateDataBelongingToUser(gameRatingToUpdate, userId, UPDATE_GAME_RATING);
        gameRatingValidator.validateNewRatingRange(updatedRatingValue, UPDATE_GAME_RATING);
        GameRating updatedGameRating = gameRatingService.updateGameRating(updatedRatingValue, gameRatingToUpdate);
        LOGGER.info("Successfully updated rating with ID: " + ratingId);

        return gameRatingMapper.mapToGameRatingDto(updatedGameRating);
    }

    public String deleteGameRating(final Long ratingId) throws GameRatingNotFoundException {
        LOGGER.info("[ADMIN] Deleting game rating with ID: " + ratingId);
        gameRatingValidator.validateDatabasePresence(ratingId, DELETE_GAME_RATING);
        gameRatingRepository.deleteById(ratingId);
        LOGGER.info("[ADMIN] Successfully deleted rating with ID: " + ratingId);

        return "Rating deleted!";
    }

    public List<GameRatingDto> fetchGameRatingListForGivenGameId(final Long gameId) throws GameNotFoundException {
        gameValidator.validateDatabasePresence(gameId, FETCH_RATINGS_FOR_GIVEN_GAME);

        return gameRatingMapper.mapToListOfGameRatingDtos(gameRatingService.getGameRatingList(gameId));
    }
}
