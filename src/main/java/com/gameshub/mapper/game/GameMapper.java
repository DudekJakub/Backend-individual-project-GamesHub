package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
import com.gameshub.domain.game.rawgGame.fromList.RawgGameFromListDto;
import com.gameshub.exceptions.GameNotFoundException;
import com.gameshub.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameMapper {

    private final GameRepository repository;

    public Game mapToGame(RawgGameDetailedDto rawgGameDetailedDto) {
        return Game.builder()
                .id(rawgGameDetailedDto.getId())
                .name(rawgGameDetailedDto.getName())
                .build();
    }

    public Game mapToGameFromId(Long gameId) throws GameNotFoundException {
        return repository
                .findById(gameId)
                .orElseThrow(GameNotFoundException::new);
    }

    public Game mapToGameFromRawgGame(RawgGameFromListDto rawgGameFromListDto) {
        return Game.builder()
                .id(rawgGameFromListDto.getId())
                .name(rawgGameFromListDto.getSlug())
                .build();
    }
}
