package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.user.User;
import com.gameshub.repository.GameRepository;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        if (gameHasUserOnWantedList(gameToMarkAsOwned, user)) {
            gameToMarkAsOwned.getUsersWantedThisGame().remove(user);
            user.getGamesWantedToOwn().remove(gameToMarkAsOwned);
        }

        gameToMarkAsOwned.getUsersOwnedThisGame().add(user);
        user.getGamesOwned().add(gameToMarkAsOwned);
        gameRepository.save(gameToMarkAsOwned);
        userRepository.save(user);
    }

    public void markGameAsWantedToOwn(final Game gameToMarkAsWantedToOwn, final User user) {
        if (gameHasUserOnOwnedList(gameToMarkAsWantedToOwn, user)) {
            gameToMarkAsWantedToOwn.getUsersOwnedThisGame().remove(user);
            user.getGamesOwned().remove(gameToMarkAsWantedToOwn);
        }

        gameToMarkAsWantedToOwn.getUsersWantedThisGame().add(user);
        user.getGamesWantedToOwn().add(gameToMarkAsWantedToOwn);
        gameRepository.save(gameToMarkAsWantedToOwn);
        userRepository.save(user);
    }

    public Set<User> getGameObservers(final Game game) {
        return game.getObservers();
    }

    public Set<Game> getGamesUserOwns(final User user) {
        return user.getGamesOwned();
    }

    public Set<Game> getGamesUserWantsToOwn(final User user) {
        return user.getGamesWantedToOwn();
    }

    private boolean gameHasUserOnOwnedList(final Game gameToCheck, final User userToCheck) {
        return gameToCheck.getUsersOwnedThisGame()
                .stream()
                .anyMatch(user -> user.equals(userToCheck));
    }

    private boolean gameHasUserOnWantedList(final Game gameToCheck, final User userToCheck) {
        return gameToCheck.getUsersWantedThisGame()
                .stream()
                .anyMatch(user -> user.equals(userToCheck));
    }
}
