package com.gameshub.service;

import com.gameshub.domain.game.rawgGame.RawgGameDetailedDto;
import com.gameshub.exception.RawgGameDetailedNotFoundException;
import com.gameshub.mapper.game.RawgGameNameMapper;
import com.gameshub.rawg.client.RawgClient;
import com.gameshub.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RawgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawgService.class);

    private final RawgClient rawgClient;
    private final GameRepository gameRepository;

    public List<RawgGameDetailedDto> fetchListOfRawgGamesRelatedToGivenName(final String name) {
        var mappedName = RawgGameNameMapper.mapGameNameToRawgSlugName(name);
        var listOfGames = gameRepository.retrieveGamesWhereNameIsLike(mappedName);
        var resultList = new ArrayList<RawgGameDetailedDto>();

        LOGGER.info("Searching for RAWG's games...");
        listOfGames.forEach(id -> {
            try {
                resultList
                        .add(rawgClient.getGameById(id)
                                .orElseThrow(RawgGameDetailedNotFoundException::new));
            } catch (RawgGameDetailedNotFoundException e) {
                LOGGER.error("Exception occurred when looking for game with id: " + id);
                e.getCause();
            }
        });
        LOGGER.info("Search done successfully");

        return resultList;
    }

    public RawgGameDetailedDto getRawgGameDetailedById(final Long id) {
        RawgGameDetailedDto resultRawgGame = null;

        LOGGER.info("Searching for RAWG's game by id...");
        try {
            resultRawgGame = rawgClient.getGameById(id).orElseThrow(RawgGameDetailedNotFoundException::new);
        } catch (RawgGameDetailedNotFoundException e) {
            LOGGER.error("Exception occurred when looking for game with id: " + id);
            e.getCause();
        }
        return resultRawgGame;
    }


}
