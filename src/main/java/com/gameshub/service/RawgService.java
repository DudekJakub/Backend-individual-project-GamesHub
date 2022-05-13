package com.gameshub.service;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
import com.gameshub.domain.game.rawgGame.fromList.RawgGameFromListDto;
import com.gameshub.domain.game.rawgGame.fromList.RawgGameListDto;
import com.gameshub.exceptions.RawgGameDetailedNotFoundException;
import com.gameshub.mapper.game.GameMapper;
import com.gameshub.rawg.client.RawgClient;
import com.gameshub.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RawgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawgService.class);

    private final RawgClient rawgClient;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public List<RawgGameDetailedDto> fetchListOfRawgGamesRelatedToGivenName(String name) {
        var mappedName = mapNameToRawgsSlugName(name);
        var listOfGames = gameRepository.retrieveGamesWhereNameIsLike(mappedName);
        var resultList = new ArrayList<RawgGameDetailedDto>();

        LOGGER.info("Searching for RAWG's games...");
        listOfGames.forEach(id -> {
            try {
                resultList
                        .add(rawgClient.getGameById(id)
                                .orElseThrow(RawgGameDetailedNotFoundException::new));
            } catch (RawgGameDetailedNotFoundException e) {
                LOGGER.error("Exception occurred when looking for game with id : " + id);
                e.getCause();
            }
        });
        LOGGER.info("Search done successfully");

        return resultList;
    }

    public RawgGameDetailedDto getRawgGameDetailedById(Long id) {
        RawgGameDetailedDto resultRawgGame = null;

        LOGGER.info("Searching for RAWG's game by id...");
        try {
            resultRawgGame = rawgClient.getGameById(id).orElseThrow(RawgGameDetailedNotFoundException::new);
        } catch (RawgGameDetailedNotFoundException e) {
            LOGGER.error("Exception occurred when looking for game with id : " + id);
            e.getCause();
        }
        return resultRawgGame;
    }

    private String mapNameToRawgsSlugName(String name) {
        return name.replaceAll(" ", "-");
    }
}
