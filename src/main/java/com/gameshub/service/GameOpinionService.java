package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.mapper.game.GameOpinionMapper;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.repository.GameRepository;
import com.gameshub.subscribe.GameOpinionAddedEvent;
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
public class GameOpinionService implements IGameObservable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameOpinionService.class);

    private final GameOpinionRepository gameOpinionRepository;
    private final SubscribeEventVisitor subscribeEventVisitor;
    private final GameOpinionMapper gameOpinionMapper;
    private final GameRepository gameRepository;

    public GameOpinion createGameOpinion(final GameOpinionDto gameOpinionDto) throws GameNotFoundException {
        Long gameId = gameOpinionDto.getGameId();
        Game newOpinionGame = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        int opinionsQnt = newOpinionGame.getGameOpinions().size();
        Set<User> observers = newOpinionGame.getObservers();
        GameOpinion newOpinion = null;

        try {
            LOGGER.info("Creating new opinion to game with given ID: " + gameId);
            newOpinion = gameOpinionMapper.mapToGameOpinion(gameOpinionDto);
            gameOpinionRepository.save(newOpinion);
            LOGGER.info("New opinion to game with given ID : " + gameId + " created successfully!");

            if (observers.size() > 0) {
                LOGGER.info("Notifying observers for game with ID: " + gameId);
                for (User observer : observers) {
                    if (!newOpinion.getUser().equals(observer)) {
                        GameOpinionAddedEvent gameOpinionAddedEvent = prepareNewOpinionEventDataForSpecificObserver(observer, newOpinion, opinionsQnt);
                        notifyObserver(gameOpinionAddedEvent);
                    }
                }
                LOGGER.info("Notifying observers for game with ID: " + gameId + " done successfully!");
            }
        } catch (GameNotFoundException | UserNotFoundException e) {
            LOGGER.error("Creating failed! Exception occurred! " + e.getMessage());
        }
        return newOpinion;
    }

    public GameOpinion updateGameOpinion(final String updatedText, final GameOpinion gameOpinion) {
        gameOpinion.setOpinion(updatedText);
        gameOpinionRepository.save(gameOpinion);
        return gameOpinion;
    }

    public List<GameOpinion> getGameOpinionListForGivenGameId(final Long gameId) {
        return gameOpinionRepository
                .findAll()
                .stream()
                .filter(gameOpinion -> gameOpinion.getGame().getId().equals(gameId))
                .collect(Collectors.toList());
    }

    @Override
    public void notifyObserver(final SubscribeEvent subscribeEvent) {
        subscribeEvent.accept(subscribeEventVisitor);
    }

    private GameOpinionAddedEvent prepareNewOpinionEventDataForSpecificObserver(final User observer, final GameOpinion newOpinion, final int opinionsQnt) {
        return new GameOpinionAddedEvent(observer, observer.getFirstname(), observer.getEmail(), newOpinion.getOpinion(), newOpinion, opinionsQnt);
    }
}
