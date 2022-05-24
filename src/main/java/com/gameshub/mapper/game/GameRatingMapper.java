package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.game.GameRatingDto;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameRatingMapper {

    private final GameMapper gameMapper;
    private final UserMapper userMapper;

    public GameRating mapToGameRating(GameRatingDto gameRatingDto) throws GameNotFoundException,
                                                                          UserNotFoundException {
        Long gameId = gameRatingDto.getGameId();
        Long userId = gameRatingDto.getUserId();
        Game gameFoundById = gameMapper.mapToGameFromId(gameId);
        User userFoundById = userMapper.mapToUserFromId(userId);

        return GameRating.builder()
                .id(gameRatingDto.getId())
                .gameName(gameFoundById.getName())
                .rating(gameRatingDto.getRating())
                .game(gameFoundById)
                .user(userFoundById)
                .build();
    }

    public GameRatingDto mapToGameRatingDto(GameRating gameRating) {
        Long gameId = gameRating.getGame().getId();
        Long userId = gameRating.getUser().getId();

        return GameRatingDto.builder()
                .id(gameRating.getId())
                .rating(gameRating.getRating())
                .publicationDate(gameRating.getPublicationDate())
                .gameId(gameId)
                .userId(userId)
                .build();
    }

    public List<GameRatingDto> mapToListOfGameRatingDtos(List<GameRating> gameRatingList) {
        return gameRatingList.stream()
                .map(gameRating -> GameRatingDto.builder()
                        .id(gameRating.getId())
                        .rating(gameRating.getRating())
                        .publicationDate(gameRating.getPublicationDate())
                        .gameId(gameRating.getGame().getId())
                        .userId(gameRating.getUser().getId())
                        .build())
                .collect(Collectors.toList());
    }
}
