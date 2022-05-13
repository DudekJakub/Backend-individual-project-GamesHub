package com.gameshub.controller;

import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
import com.gameshub.service.RawgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/games/")
public class RawgGameController {

    private final RawgService rawgService;

    @GetMapping(value = "{name}")
    public ResponseEntity<List<RawgGameDetailedDto>> rawgGamesRelatedToGivenName(@PathVariable String name) {
        return ResponseEntity.ok(rawgService.fetchListOfRawgGamesRelatedToGivenName(name));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<RawgGameDetailedDto> rawgGameById(@PathVariable Long id) {
        return ResponseEntity.ok(rawgService.getRawgGameDetailedById(id));
    }
}
