package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.domain.user.User;
import com.gameshub.exceptions.GameNotFoundException;
import com.gameshub.exceptions.UserNotFoundException;
import com.gameshub.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameOpinionMapper {

    private final GameMapper gameMapper;
    private final UserMapper userMapper;

    public GameOpinion mapToGameOpinion(GameOpinionDto gameOpinionDto) throws GameNotFoundException,
                                                                              UserNotFoundException {
        Long gameId = gameOpinionDto.getGameId();
        Long userId = gameOpinionDto.getUserId();
        Game gameFoundById = gameMapper.mapToGameFromId(gameId);
        User userFoundById = userMapper.mapToUserFromId(userId);

        return GameOpinion.builder()
                .id(gameOpinionDto.getId())
                .gameName(gameOpinionDto.getGameName())
                .userLogin(gameOpinionDto.getUserLogin())
                .opinion(gameOpinionDto.getOpinion())
                .game(gameFoundById)
                .user(userFoundById)
                .build();
    }

    public GameOpinionDto mapToGameOpinionDto(GameOpinion gameOpinion) {
        Long gameId = gameOpinion.getGame().getId();
        Long userId = gameOpinion.getUser().getId();

        return GameOpinionDto.builder()
                .id(gameOpinion.getId())
                .gameName(gameOpinion.getGameName())
                .userLogin(gameOpinion.getUserLogin())
                .opinion(gameOpinion.getOpinion())
                .gameId(gameId)
                .userId(userId)
                .build();
    }
}
