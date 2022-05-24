package com.gameshub.facade;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameDto;
import com.gameshub.domain.user.User;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.exception.*;
import com.gameshub.mapper.game.GameMapper;
import com.gameshub.mapper.user.UserMapper;
import com.gameshub.repository.GameRepository;
import com.gameshub.repository.UserRepository;
import com.gameshub.service.GameService;
import com.gameshub.validator.SubscribeValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.gameshub.facade.OperationName.*;

@Component
@RequiredArgsConstructor
public class GameFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameFacade.class);

    private final GameService gameService;
    private final GameMapper gameMapper;
    private final UserMapper userMapper;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private final SubscribeValidator subscribeValidator;

    public String subscribeGame(final Long gameId, final Long userId) throws GameNotFoundException, UserNotFoundException,
                                                                             GameAlreadySubscribedException {
        String operationDoneSuccessfully = "Game with ID: " + gameId + " subscribed successfully!";
        Game gameToSubscribe = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        User subscriber = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        LOGGER.info("Subscribing game with ID: " + gameId + " for user with ID : " + userId);
        subscribeValidator.validateGameObserversPresence(gameToSubscribe, subscriber, SUBSCRIBE_GAME);
        gameService.subscribeGame(gameToSubscribe, subscriber);
        LOGGER.info(operationDoneSuccessfully);

        return operationDoneSuccessfully;
    }

    public String unsubscribeGame(final Long gameId, final Long userId) throws GameNotFoundException, UserNotFoundException,
                                                                               GameNotSubscribedException {
        String operationDoneSuccessfully = "Game with ID: " + gameId + " unsubscribed successfully!";
        Game gameToUnsubscribe = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        User subscriber = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        LOGGER.info("Unsubscribing game with ID: " + gameId + " for user with ID: " + userId);
        subscribeValidator.validateGameObserversAbsence(gameToUnsubscribe, subscriber, UNSUBSCRIBE_GAME);
        gameService.unsubscribeGame(gameToUnsubscribe, subscriber);
        LOGGER.info(operationDoneSuccessfully);

        return operationDoneSuccessfully;
    }

    public GameDto markGameAsOwned(final Long gameId, final Long userId) throws GameNotFoundException, UserNotFoundException {
        Game gameToMarkAsOwned = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        LOGGER.info("Marking game with ID: " + gameId + " as owned by user with ID: " + userId);
        gameService.markGameAsOwned(gameToMarkAsOwned, user);
        LOGGER.info("Gamed marked as owned successfully!");

        return gameMapper.mapToGameDto(gameToMarkAsOwned);
    }

    public GameDto markGameAsWantedToOwn(final Long gameId, final Long userId) throws GameNotFoundException, UserNotFoundException {
        Game gameToMarkAsWantedToOwn = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        LOGGER.info("Marking game with ID: " + gameId + " as wanted by user with ID: " + userId);
        gameService.markGameAsWantedToOwn(gameToMarkAsWantedToOwn, user);
        LOGGER.info("Gamed marked as wanted successfully!");

        return gameMapper.mapToGameDto(gameToMarkAsWantedToOwn);
    }

    public Set<UserOpenDto> fetchGameObservers(final Long gameId) throws GameNotFoundException {
        Game gameForObserversSet = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);

        return userMapper.mapToUserOpenDtoSet(gameService.getGameObservers(gameForObserversSet));
    }

    public Set<GameDto> fetchGamesUserOwns(final Long userId) throws UserNotFoundException {
        User userForGamesItOwns = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return gameMapper.mapToGameSetDto(gameService.getGamesUserOwns(userForGamesItOwns));
    }

    public Set<GameDto> fetchGamesUserWantsToOwn(final Long userId) throws UserNotFoundException {
        User userForGamesItWantsToOwn = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return gameMapper.mapToGameSetDto(gameService.getGamesUserWantsToOwn(userForGamesItWantsToOwn));
    }
}
