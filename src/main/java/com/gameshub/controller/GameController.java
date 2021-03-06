package com.gameshub.controller;

import com.gameshub.domain.game.GameDto;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.exception.*;
import com.gameshub.facade.GameFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/games")
public class GameController {

    private final GameFacade facade;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @PostMapping("/subscribe/{gameId}/{userId}")
    public ResponseEntity<String> subscribeGame(@PathVariable Long gameId, @PathVariable Long userId) throws UserNotFoundException, GameNotFoundException,
                                                                                                             GameAlreadySubscribedException {
        return ResponseEntity.ok(facade.subscribeGame(gameId, userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @PostMapping("/markOwned/{gameId}/{userId}")
    public ResponseEntity<GameDto> markGameAsOwnedByUser(@PathVariable Long gameId, @PathVariable Long userId) throws UserNotFoundException, GameNotFoundException {
        return ResponseEntity.ok(facade.markGameAsOwned(gameId, userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @PostMapping("/markWanted/{gameId}/{userId}")
    public ResponseEntity<GameDto> markGameAsWantedToOwnByUser(@PathVariable Long gameId, @PathVariable Long userId) throws UserNotFoundException, GameNotFoundException {
        return ResponseEntity.ok(facade.markGameAsWantedToOwn(gameId, userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/owned/{userId}")
    public ResponseEntity<Set<GameDto>> gamesUserOwns(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(facade.fetchGamesUserOwns(userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/wanted/{userId}")
    public ResponseEntity<Set<GameDto>> gamesUserWants(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(facade.fetchGamesUserWantsToOwn(userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/observers/{gameId}")
    public ResponseEntity<Set<UserOpenDto>> gameObservers(@PathVariable Long gameId) throws GameNotFoundException {
        return ResponseEntity.ok(facade.fetchGameObservers(gameId));
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @DeleteMapping("/subscribe/{gameId}/{userId}")
    public ResponseEntity<String> unsubscribeGame(@PathVariable Long gameId, @PathVariable Long userId) throws UserNotFoundException, GameNotSubscribedException,
                                                                                                               GameNotFoundException {
        return ResponseEntity.ok(facade.unsubscribeGame(gameId, userId));
    }
}
