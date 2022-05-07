package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
import com.gameshub.domain.game.rawgGame.fromList.RawgGameFromListDto;
import com.gameshub.domain.game.rawgGame.fromList.RawgGameListDto;
import com.gameshub.mapper.game.GameMapper;
import com.gameshub.rawg.client.RawgClient;
import com.gameshub.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RawgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawgService.class);

    private final RawgClient rawgClient;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public void fillDatabaseWithGames() {
        int pageCounter = 1;

        while (pageCounter <= 36406) {
            List<RawgGameFromListDto> gamesFromList = rawgClient.getGamesList(pageCounter).get(0).getResults();
            gamesFromList.forEach(rawgGameFromListDto -> {
                Game game = gameMapper.mapToGameFromRawgGame(rawgGameFromListDto);
                gameRepository.save(game);
            });
            pageCounter++;
        }
    }



    private List<RawgGameFromListDto> fetchAllGamesFromCurrentPage(int pageNumber) {
        return rawgClient
                .getGamesList(pageNumber)
                .get(0)
                .getResults();
    }
}
