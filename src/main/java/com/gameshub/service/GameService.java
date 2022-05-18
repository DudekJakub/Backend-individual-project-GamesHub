package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.user.User;
import com.gameshub.repository.GameRepository;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public void subscribeGame(final Game gameToSubscribe, final User subscriber) {
        gameToSubscribe.getObservers().add(subscriber);
        subscriber.getObservedGames().add(gameToSubscribe);
        gameRepository.save(gameToSubscribe);
        userRepository.save(subscriber);
    }

    public void unsubscribeGame(final Game gameToUnsubscribe, final User subscriber) {
        gameToUnsubscribe.getObservers().remove(subscriber);
        subscriber.getObservedGames().remove(gameToUnsubscribe);
        gameRepository.save(gameToUnsubscribe);
        userRepository.save(subscriber);
    }

    public void markGameAsOwned(final Game gameToMarkAsOwned, final User user) {
        if (checkIfGameContainsUserWhoWantsToOwnIt(gameToMarkAsOwned, user)) {
            gameToMarkAsOwned.getUsersWantedToOwnThisGame().remove(user);
            user.getGamesWantedToOwn().remove(gameToMarkAsOwned);
        }

        gameToMarkAsOwned.getUsersOwnedThisGame().add(user);
        user.getGamesOwned().add(gameToMarkAsOwned);
        gameRepository.save(gameToMarkAsOwned);
        userRepository.save(user);
    }

    public void markGameAsWantedToOwn(final Game gameToMarkAsWantedToOwn, final User user) {
        if (checkIfGameContainsUserAsOwner(gameToMarkAsWantedToOwn, user)) {
            gameToMarkAsWantedToOwn.getUsersOwnedThisGame().remove(user);
            user.getGamesOwned().remove(gameToMarkAsWantedToOwn);
        }

        gameToMarkAsWantedToOwn.getUsersWantedToOwnThisGame().add(user);
        user.getGamesWantedToOwn().add(gameToMarkAsWantedToOwn);
        gameRepository.save(gameToMarkAsWantedToOwn);
        userRepository.save(user);
    }

    public Set<User> getAllGameObservers(final Game gameForObserversSet) {
        return gameForObserversSet.getObservers();
    }

    public Set<Game> getGamesUserOwns(final User userForGamesItOwns) {
        return userForGamesItOwns.getGamesOwned();
    }

    public Set<Game> getGamesUserWantsToOwn(final User userForGamesItWantsToOwn) {
        return userForGamesItWantsToOwn.getGamesWantedToOwn();
    }

    public List<GameOpinion> getThreeLatestGameOpinions(final Game gameForOpinionsList) {
        int gameOpinionsSize = gameForOpinionsList.getGameOpinions().size();

        return gameForOpinionsList.getGameOpinions()
                                  .stream()
                                  .sorted(Comparator.comparing(GameOpinion::getPublicationDate))
                                  .collect(Collectors.toList())
                                  .subList(gameOpinionsSize-3,gameOpinionsSize)
                                  .stream()
                                  .sorted(Collections.reverseOrder(Comparator.comparing(GameOpinion::getPublicationDate)))
                                  .collect(Collectors.toList());
    }

    private boolean checkIfGameContainsUserAsOwner(final Game gameToCheck, final User userToCheck) {
        return gameToCheck.getUsersOwnedThisGame()
                .stream()
                .anyMatch(user -> user.equals(userToCheck));
    }

    private boolean checkIfGameContainsUserWhoWantsToOwnIt(final Game gameToCheck, final User userToCheck) {
        return gameToCheck.getUsersWantedToOwnThisGame()
                .stream()
                .anyMatch(user -> user.equals(userToCheck));
    }
}
