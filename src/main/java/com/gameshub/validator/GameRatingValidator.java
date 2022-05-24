package com.gameshub.validator;

import com.gameshub.domain.user.User;
import com.gameshub.exception.GameAlreadyRatedByUserException;
import com.gameshub.exception.GameRatingNotFoundException;
import com.gameshub.exception.GameRatingOutOfRangeException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.GameRatingRepository;
import com.gameshub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameRatingValidator extends GameDataValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRatingValidator.class);

    private final GameRatingRepository gameRatingRepository;

    @Autowired
    public GameRatingValidator(final UserRepository userRepository, final GameRatingRepository gameRatingRepository) {
        super(userRepository);
        this.gameRatingRepository = gameRatingRepository;
    }

    public void validateNewRatingRange(final double newRating, final String operationName) throws GameRatingOutOfRangeException {
        if (newRating < 0.0 || newRating > 10.0) {
            LOGGER.warn(operationName + "Validation failed! Game's rating is out of range!");
            throw new GameRatingOutOfRangeException();
        }
    }

    public void validateNewRatingPresence(final Long gameId, final Long userId, final String operationName) throws UserNotFoundException, GameAlreadyRatedByUserException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        boolean userAlreadyRatedGame = user.getGameRatings()
                                           .stream()
                                           .anyMatch(userGameRating -> userGameRating.getGame().getId().equals(gameId));

        if (userAlreadyRatedGame) {
            LOGGER.warn(operationName + "Validation failed! User already rated this game!");
            throw new GameAlreadyRatedByUserException();
        }
    }

    public void validateDatabasePresence(final Long gameRatingId, final String operationName) throws GameRatingNotFoundException {
        boolean existInDatabase = gameRatingRepository.findById(gameRatingId).isPresent();

        if (!existInDatabase) {
            LOGGER.warn(operationName + "Validation failed! Game rating not found!");
            throw new GameRatingNotFoundException();
        }
    }
}
