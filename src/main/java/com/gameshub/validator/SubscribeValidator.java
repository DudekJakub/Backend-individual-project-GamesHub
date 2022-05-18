package com.gameshub.validator;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameAlreadySubscribedException;
import com.gameshub.exception.GameNotSubscribedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscribeValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscribeValidator.class);

    public void validateGameObserversPresence(final Game gameToSubscribe, final User subscriber, final String operationName) throws GameAlreadySubscribedException {
        Set<User> gameObservers = gameToSubscribe.getObservers();

        if (gameObservers.contains(subscriber)) {
            LOGGER.error(operationName + "Validation failed! User is already subscribing given game!");
            throw new GameAlreadySubscribedException();
        }
    }

    public void validateGameObserversAbsence(final Game gameToUnsubscribe, final User subscriber, final String operationName) throws GameNotSubscribedException {
        Set<User> gameObservers = gameToUnsubscribe.getObservers();

        if (!gameObservers.contains(subscriber)) {
            LOGGER.error(operationName + "Validation failed! User is NOT subscribing given game!");
            throw new GameNotSubscribedException();
        }
    }
}
