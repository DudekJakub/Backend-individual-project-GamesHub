package com.gameshub.service.outern_api;

import com.gameshub.domain.game.rawgGame.RawgGameDetailedDto;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.GameSearchNotFoundException;
import com.gameshub.exception.RawgGameDetailedNotFoundException;
import com.gameshub.mapper.game.RawgGameNameMapper;
import com.gameshub.client.RawgClient;
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

    public List<RawgGameDetailedDto> fetchListOfRawgGamesRelatedToGivenName(final String name) throws GameSearchNotFoundException {
        String mappedName = RawgGameNameMapper.mapGameNameToRawgSlugName(name);
        List<Long> listOfGames = gameRepository.retrieveGamesWhereNameIsLike(mappedName);
        List<RawgGameDetailedDto> resultList = new ArrayList<>();

        if (listOfGames.size() == 0) {
            throw new GameSearchNotFoundException();
        }

        listOfGames.forEach(id -> {
            try {
                resultList.add(rawgClient.getGameById(id).orElseThrow(RawgGameDetailedNotFoundException::new));
            } catch (RawgGameDetailedNotFoundException e) {
                LOGGER.warn("Exception occurred when looking for game with id: " + id);
                e.getCause();
            }
        });
        return resultList;
    }

    public RawgGameDetailedDto getRawgGameDetailedById(final Long id) {
        RawgGameDetailedDto resultRawgGame = null;

        try {
            resultRawgGame = rawgClient.getGameById(id).orElseThrow(RawgGameDetailedNotFoundException::new);
        } catch (RawgGameDetailedNotFoundException e) {
            LOGGER.warn("Exception occurred when looking for game with id: " + id);
            e.getCause();
        }
        return resultRawgGame;
    }
}
