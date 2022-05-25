package com.gameshub.validator;

import com.gameshub.domain.game.GameDataSource;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameDataUpdateAccessDeniedException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GameDataValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameDataValidator.class);

    protected final UserRepository userRepository;

    @Autowired
    public GameDataValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateDataBelongingToUser(final GameDataSource gameDataSource, final Long userId, final String operationName) throws UserNotFoundException, GameDataUpdateAccessDeniedException {
        User userToCheck = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        long user_id = userToCheck.getId();
        long gameDataSourceUserId = gameDataSource.getUser().getId();

        if (!(gameDataSourceUserId == user_id)) {
            LOGGER.error(operationName + "Validation failed! Given game's " + getDataType(gameDataSource) + " does not belong to currently logged user!");
            throw new GameDataUpdateAccessDeniedException();
        }
    }

    private String getDataType(final GameDataSource gameDataSource) {
        int gameWordLength = "game".length();
        return gameDataSource.getClass().getSimpleName().substring(gameWordLength);
    }
}
