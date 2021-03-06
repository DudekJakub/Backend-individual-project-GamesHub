package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameDto;
import com.gameshub.domain.game.rawgGame.RawgGameDetailedDto;
import com.gameshub.domain.game.rawgGame.RawgGameFromListDto;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameMapper {

    private final GameRepository repository;

    public Game mapToGame(final RawgGameDetailedDto rawgGameDetailedDto) {
        return Game.builder()
                .id(rawgGameDetailedDto.getId())
                .name(rawgGameDetailedDto.getName())
                .build();
    }

    public GameDto mapToGameDto(final Game game) {
        return GameDto.builder()
                .id(game.getId())
                .name(game.getName())
                .averageRating(game.getAverageRating())
                .opinionsQnt(game.getOpinionsQnt())
                .ratingsQnt(game.getRatingsQnt())
                .build();
    }

    public Game mapToGameFromId(final Long gameId) throws GameNotFoundException {
        return repository
                .findById(gameId)
                .orElseThrow(GameNotFoundException::new);
    }

    public Set<GameDto> mapToGameSetDto(final Set<Game> games) {
        return games.stream()
                .map(game -> GameDto.builder()
                        .id(game.getId())
                        .name(game.getName())
                        .build())
                .collect(Collectors.toSet());
    }
}
