package com.gameshub.client;

import com.gameshub.domain.game.rawgGame.RawgGameDetailedDto;
import com.gameshub.domain.game.rawgGame.RawgGameListDto;
import com.gameshub.client.config.RawgApiConfig;
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
    private final RawgApiConfig rawgApiConfig;

    private URI URI_GAMES_LIST(final int pageNumber) {
        return UriComponentsBuilder.fromHttpUrl(rawgApiConfig.getRawgApiEndpoint() + "games")
                .queryParam("key", rawgApiConfig.getRawgAppKey())
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

    private URI URI_GAME_BY_ID(final Long gameId) {
        return UriComponentsBuilder.fromHttpUrl(rawgApiConfig.getRawgApiEndpoint() + "games/" + gameId)
                .queryParam("key", rawgApiConfig.getRawgAppKey())
                .build()
                .encode()
                .toUri();
    }

    private URI URI_GAME_BY_NAME(final String name) {
        return UriComponentsBuilder.fromHttpUrl(rawgApiConfig.getRawgApiEndpoint() + "games/" + name)
                .queryParam("key", rawgApiConfig.getRawgAppKey())
                .build()
                .encode()
                .toUri();
    }

    public List<RawgGameListDto> getGamesList(final int pageNumber) {
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

    public Optional<RawgGameDetailedDto> getGameByName(final String name) {
        URI url = URI_GAME_BY_NAME(name);
        try {
            RawgGameDetailedDto gameResponse = restTemplate.getForObject(url, RawgGameDetailedDto.class);
            return Optional.ofNullable(gameResponse);
        } catch (NoSuchElementException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<RawgGameDetailedDto> getGameById(final Long gameId) {
        URI url = URI_GAME_BY_ID(gameId);
        try {
            RawgGameDetailedDto gameResponse = restTemplate.getForObject(url, RawgGameDetailedDto.class);
            return Optional.ofNullable(gameResponse);
        } catch (NoSuchElementException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
