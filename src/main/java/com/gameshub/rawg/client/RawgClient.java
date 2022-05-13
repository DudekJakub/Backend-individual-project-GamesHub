package com.gameshub.rawg.client;

import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
import com.gameshub.domain.game.rawgGame.fromList.RawgGameListDto;
import com.gameshub.rawg.config.RawgConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class RawgClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawgClient.class);

    private final RestTemplate restTemplate;
    private final RawgConfig rawgConfig;

    private URI URI_GAMES_LIST(int pageNumber) {
        return UriComponentsBuilder.fromHttpUrl(rawgConfig.getRawgApiEndpoint() + "games")
                .queryParam("key", rawgConfig.getRawgAppKey())
                .queryParam("page", pageNumber)
                .queryParam("exclude_additions", "true")
                .queryParam("exclude_parents", "false")
                .queryParam("exclude_game_series", "false")
                .queryParam("metacritic", "50,100")
                .queryParam("stores", "1,2,3,5,6,11")
                .build()
                .encode()
                .toUri();
    }

    private URI URI_GAME_BY_ID(Long gameId) {
        return UriComponentsBuilder.fromHttpUrl(rawgConfig.getRawgApiEndpoint() + "games/" + gameId)
                .queryParam("key", rawgConfig.getRawgAppKey())
                .build()
                .encode()
                .toUri();
    }

    private URI URI_GAME_BY_NAME(String name) {
        return UriComponentsBuilder.fromHttpUrl(rawgConfig.getRawgApiEndpoint() + "games/" + name)
                .queryParam("key", rawgConfig.getRawgAppKey())
                .build()
                .encode()
                .toUri();
    }

    public List<RawgGameListDto> getGamesList(int pageNumber) {
        URI url = URI_GAMES_LIST(pageNumber);
        try {
            RawgGameListDto gamesResponse = restTemplate.getForObject(url, RawgGameListDto.class);
            return new ArrayList<>(Optional.ofNullable(gamesResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Optional<RawgGameDetailedDto> getGameByName(String name) {
        URI url = URI_GAME_BY_NAME(name);
        try {

            RawgGameDetailedDto gameResponse = restTemplate.getForObject(url, RawgGameDetailedDto.class);
            return Optional.ofNullable(gameResponse);

        } catch (NoSuchElementException|RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<RawgGameDetailedDto> getGameById(Long gameId) {
        URI url = URI_GAME_BY_ID(gameId);
        try {

            RawgGameDetailedDto gameResponse = restTemplate.getForObject(url, RawgGameDetailedDto.class);
            return Optional.ofNullable(gameResponse);

        } catch (RestClientException|NoSuchElementException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
