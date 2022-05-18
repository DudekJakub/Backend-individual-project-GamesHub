package com.gameshub.mapper.game;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameOpinionDto;
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
                .gameName(gameFoundById.getName())
                .userLogin(userFoundById.getLoginName())
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
                .opinion(gameOpinion.getOpinion())
                .publicationDate(gameOpinion.getPublicationDate())
                .gameId(gameId)
                .userId(userId)
                .build();
    }

    public List<GameOpinionDto> mapToListOfGameOpinionDtos(List<GameOpinion> gameOpinionList) {
        return gameOpinionList.stream()
                .map(gameOpinion -> GameOpinionDto.builder()
                    .id(gameOpinion.getId())
                    .opinion(gameOpinion.getOpinion())
                    .publicationDate(gameOpinion.getPublicationDate())
                    .gameId(gameOpinion.getGame().getId())
                    .userId(gameOpinion.getUser().getId())
                    .build())
                .collect(Collectors.toList());
    }
}
