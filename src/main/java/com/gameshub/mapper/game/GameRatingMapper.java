package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.game.GameRatingDto;
import com.gameshub.domain.user.User;
import com.gameshub.exceptions.GameNotFoundException;
import com.gameshub.exceptions.UserNotFoundException;
import com.gameshub.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .gameName(gameRatingDto.getGameName())
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
                .gameName(gameRating.getGameName())
                .rating(gameRating.getRating())
                .gameId(gameId)
                .userId(userId)
                .build();
    }
}
