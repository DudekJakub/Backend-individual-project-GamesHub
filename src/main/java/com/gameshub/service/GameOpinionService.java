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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameOpinionService implements GameObservable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameOpinionService.class);

    private final ApplicationContext appContext;
    private final GameOpinionRepository gameOpinionRepository;
    private final SubscribeEventVisitor subscribeEventVisitor;
    private final GameOpinionMapper gameOpinionMapper;
    private final GameRepository gameRepository;

    public GameOpinion createGameOpinion(final GameOpinionDto gameOpinionDto) throws GameNotFoundException {
        Long gameId = gameOpinionDto.getGameId();
        Game newOpinionGame = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        Set<User> observers = newOpinionGame.getObservers();
        GameOpinion newOpinion = null;

        try {
            LOGGER.info("Creating new opinion to game with ID: " + gameId);
            newOpinion = gameOpinionMapper.mapToGameOpinion(gameOpinionDto);
            GameOpinion savedOpinion = gameOpinionRepository.save(newOpinion);
            LOGGER.info("New opinion to game with ID : " + gameId + " created successfully!");
            if (observers.size() > 0) {
                LOGGER.info("[NEW GAME OPINION] Notifying observers for game with ID: " + gameId);
                for (User observer : observers) {
                    if (!savedOpinion.getUser().equals(observer)) {
                        GameOpinionAddedEvent newOpinionEvent = prepareNewOpinionEventDataForSpecificObserver(observer, savedOpinion);
                        notifyObserver(newOpinionEvent);
                    }
                }
                LOGGER.info("[NEW GAME OPINION] Notifying observers for game with ID: " + gameId + " done successfully!");
            }
        } catch (GameNotFoundException | UserNotFoundException e) {
            LOGGER.warn("Creating failed! Exception occurred! " + e.getMessage());
        }

        return newOpinion;
    }

    public GameOpinion updateGameOpinion(final String updatedText, final GameOpinion gameOpinionToUpdate) {
        gameOpinionToUpdate.setOpinion(updatedText);
        gameOpinionRepository.save(gameOpinionToUpdate);

        return gameOpinionToUpdate;
    }

    public List<GameOpinion> getGameOpinionList(final Long gameId) {
        return gameOpinionRepository
                .findAll()
                .stream()
                .filter(gameOpinion -> gameOpinion.getGame().getId().equals(gameId))
                .collect(Collectors.toList());
    }

    public List<GameOpinion> getFourLatestGameOpinions(final Game gameForOpinionsList) {
        return gameOpinionRepository.retrieveThreeLatestGameOpinionsForGame(gameForOpinionsList.getId());
    }

    @Override
    public void notifyObserver(final SubscribeEvent subscribeEvent) {
        subscribeEvent.accept(subscribeEventVisitor);
    }

    public String censorProfanities(final String opinionWithProfanities) {
       List<?> profanities = (List<?>) appContext.getBean("getProfanities");
       StringBuilder censoredOpinion = new StringBuilder();

        for (String word : opinionWithProfanities.split(" ")) {
            if (profanities.contains(word)) {
                word = "[censored]";
            }
            censoredOpinion.append(" ").append(word);
        }
        return censoredOpinion.toString();
    }

    private GameOpinionAddedEvent prepareNewOpinionEventDataForSpecificObserver(final User observer, final GameOpinion newOpinion) {
        return new GameOpinionAddedEvent(observer, newOpinion.getOpinion(), newOpinion);
    }
}
