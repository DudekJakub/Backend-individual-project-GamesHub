package com.gameshub.controller;

import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.exception.*;
import com.gameshub.facade.GameOpinionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/opinions")
public class GameOpinionController {

    private final GameOpinionFacade facade;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameOpinionDto> createGameOpinion(@RequestBody GameOpinionDto gameOpinionDto) throws GameNotFoundException, GameOpinionLengthTooLongException {
        return ResponseEntity.ok(facade.createGameOpinion(gameOpinionDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @PatchMapping(value = "/{opinionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameOpinionDto> updateGameOpinion(@RequestBody String opinionToUpdate, @PathVariable Long opinionId, @RequestParam Long userId) throws UserNotFoundException, GameOpinionNotFoundException, GameOpinionUpdateTimeExpiredException,
                                                                                                                                                                 GameOpinionLengthTooLongException, GameDataUpdateAccessDeniedException {
        return ResponseEntity.ok(facade.updateGameOpinion(opinionToUpdate, userId, opinionId));
    }

    @PreAuthorize("hasAuthority('ADMIN') and @userValidator.hasValidatedAccount()")
    @DeleteMapping(value = "{opinionId}")
    public ResponseEntity<String> deleteGameOpinion(@PathVariable Long opinionId) throws GameOpinionNotFoundException {
        return ResponseEntity.ok(facade.deleteGameOpinion(opinionId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/{gameId}")
    public ResponseEntity<List<GameOpinionDto>> gameOpinionsList(@PathVariable Long gameId) throws GameNotFoundException {
        return ResponseEntity.ok(facade.fetchGameOpinionListForGivenGameId(gameId));
    }
}
