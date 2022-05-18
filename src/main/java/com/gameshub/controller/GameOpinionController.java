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

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameOpinionDto> createGameOpinion(@RequestBody GameOpinionDto gameOpinionDto) throws UserNotFoundException, UserNotVerifiedException,
                                                                                                               GameOpinionWithProfanitiesException, GameNotFoundException {
        return ResponseEntity.ok(facade.createGameOpinion(gameOpinionDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PatchMapping(value = "/{opinionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameOpinionDto> updateGameOpinion(@RequestBody String opinionToUpdate, @PathVariable Long opinionId) throws UserNotFoundException, GameOpinionNotFoundException,
                                                                                                                                      GameOpinionUpdateTimeExpiredException, GameOpinionWithProfanitiesException,
                                                                                                                                      GameOpinionUpdateAccessDeniedException {
        return ResponseEntity.ok(facade.updateGameOpinion(opinionToUpdate, opinionId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/{gameId}")
    public ResponseEntity<List<GameOpinionDto>> gameOpinionsList(@PathVariable Long gameId) throws GameOpinionsListNotFoundException, GameNotFoundException {
        return ResponseEntity.ok(facade.fetchGameOpinionListForGivenGameId(gameId));
    }
}
