package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
import com.gameshub.domain.game.rawgGame.fromList.RawgGameFromListDto;
import org.springframework.stereotype.Service;

@Service
public class GameMapper {

    public Game mapToGame(RawgGameDetailedDto rawgGameDetailedDto) {
        return Game.builder()
                .id(rawgGameDetailedDto.getId())
                .name(rawgGameDetailedDto.getName())
                .build();
    }

    public Game mapToGameFromRawgGame(RawgGameFromListDto rawgGameFromListDto) {
        return Game.builder()
                .id(rawgGameFromListDto.getId())
                .name(rawgGameFromListDto.getSlug())
                .build();
    }
}
