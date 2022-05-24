package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.game.GameRatingDto;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.mapper.game.GameRatingMapper;
import com.gameshub.repository.GameRatingRepository;
import com.gameshub.repository.GameRepository;
import com.gameshub.subscribe.GameRatingAddedEvent;
import com.gameshub.subscribe.SubscribeEvent;
import com.gameshub.subscribe.SubscribeEventVisitor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameRatingService implements GameObservable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRatingService.class);

    private final SubscribeEventVisitor subscribeEventVisitor;
    private final GameRatingRepository gameRatingRepository;
    private final GameRatingMapper gameRatingMapper;
    private final GameRepository gameRepository;

    public GameRating createGameRating(final GameRatingDto gameRatingDto) throws GameNotFoundException {
        Long gameId = gameRatingDto.getGameId();
        Game newRatingGame = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        Set<User> observers = newRatingGame.getObservers();
        GameRating newRating = null;

        try {
            LOGGER.info("Creating new rating to game with ID: " + gameId);
            newRating = gameRatingMapper.mapToGameRating(gameRatingDto);
            GameRating savedRating = gameRatingRepository.save(newRating);
            LOGGER.info("New rating to game with ID: " + gameId + " created successfully!");

            if (observers.size() > 0 && checkIfGameIsReadyToNotifyObservers(newRatingGame)) {
                LOGGER.info("[NEW GAME RATING] Notifying observers for game with ID: " + gameId);
                for (User observer : observers) {
                    GameRatingAddedEvent newRatingEvent = prepareNewRatingEventDataForSpecificObserver(observer, savedRating);
                    notifyObserver(newRatingEvent);
                }
                LOGGER.info("[NEW GAME RATING] Notifying observers for game with ID: " + gameId + " done successfully!");
            }
        } catch (GameNotFoundException | UserNotFoundException e) {
            LOGGER.warn("Creating failed! Exception occurred! " + e.getMessage());
        }

        return newRating;
    }

    public GameRating updateGameRating(final double updatedRating, final GameRating gameRatingToUpdate) {
        gameRatingToUpdate.setRating(updatedRating);
        gameRatingRepository.save(gameRatingToUpdate);

        return gameRatingToUpdate;
    }

    public List<GameRating> getGameRatingList(final Long gameId) {
        return gameRatingRepository
                .findAll()
                .stream()
                .filter(gameRating -> gameRating.getGame().getId().equals(gameId))
                .collect(Collectors.toList());
    }

    @Override
    public void notifyObserver(final SubscribeEvent subscribeEvent) {
        subscribeEvent.accept(subscribeEventVisitor);
    }

    private boolean checkIfGameIsReadyToNotifyObservers(final Game game) {
        return (game.getGameRatings().size()) % 5 == 0;
    }

    private GameRatingAddedEvent prepareNewRatingEventDataForSpecificObserver(final User observer, final GameRating newRating) {
        return new GameRatingAddedEvent(observer, newRating.getRating(), newRating);
    }
}
