package com.gameshub.controller;

import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.domain.game.GameRatingDto;
import com.gameshub.exception.*;
import com.gameshub.facade.GameRatingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ratings")
public class GameRatingController {

    private final GameRatingFacade facade;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameRatingDto> createGameRating(@RequestBody GameRatingDto gameRatingDto) throws UserNotFoundException, GameNotFoundException,
                                                                                                           GameRatingOutOfRangeException, GameAlreadyRatedByUserException {
        return ResponseEntity.ok(facade.createGameRating(gameRatingDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @PatchMapping(value = "{ratingId}")
    public ResponseEntity<GameRatingDto> updateGameRating(@RequestParam double updatedRating, @PathVariable Long ratingId) throws UserNotFoundException, GameRatingNotFoundException,
                                                                                                                                  GameRatingOutOfRangeException, GameDataUpdateAccessDeniedException  {
        return ResponseEntity.ok(facade.updateGameRating(updatedRating, ratingId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/{gameId}")
    public ResponseEntity<List<GameRatingDto>> gameRatingsList(@PathVariable Long gameId) throws GameNotFoundException {
        return ResponseEntity.ok(facade.fetchGameRatingListForGivenGameId(gameId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "{ratingId}")
    public ResponseEntity<String> deleteGameRating(@PathVariable Long ratingId) throws GameRatingNotFoundException {
        return ResponseEntity.ok(facade.deleteGameRating(ratingId));
    }
}
