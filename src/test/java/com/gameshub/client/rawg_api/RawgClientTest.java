package com.gameshub.client.rawg_api;

import com.gameshub.client.RawgClient;
import com.gameshub.domain.game.rawgGame.RawgGameDetailedDto;
import com.gameshub.domain.game.rawgGame.RawgGameFromListDto;
import com.gameshub.domain.game.rawgGame.RawgGameListDto;
import com.gameshub.client.config.RawgApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RawgClientTest {

    @InjectMocks
    private RawgClient rawgClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RawgApiConfig rawgApiConfig;

    private static final String TEST_URI = "https://test.com/";
    private static final String TEST_KEY = "test";
    private static final String QUERY_PARAMS_FOR_TEST_URI = "&page=1" +
                                                            "&exclude_additions=true" +
                                                            "&exclude_parents=false" +
                                                            "&exclude_game_series=false" +
                                                            "&metacritic=50,100" +
                                                            "&stores=1,2,3,5,6,11";

    @BeforeEach
    public void setWhenStatement() {
        when(rawgApiConfig.getRawgApiEndpoint()).thenReturn(TEST_URI);
        when(rawgApiConfig.getRawgAppKey()).thenReturn(TEST_KEY);
    }

    @Test
    void shouldFetchGamesList() throws URISyntaxException {
        //Given
        RawgGameFromListDto gameFromList = new RawgGameFromListDto(
                1L,
                "test_game",
                "test_game",
                LocalDate.now(),
                false,
                "https://test_image.com",
                90.0,
                new ArrayList<>());

        RawgGameListDto gamesList = RawgGameListDto.builder()
                .count(100L)
                .next("https://api.rawg.io/api/games?key=" + TEST_KEY + "&page=2")
                .previous(null)
                .results(List.of(gameFromList))
                .build();

        URI uri = new URI(TEST_URI + "games?key=" + TEST_KEY + QUERY_PARAMS_FOR_TEST_URI);

        when(restTemplate.getForObject(uri, RawgGameListDto.class)).thenReturn(gamesList);

        //When
        List<RawgGameListDto> fetchedGamesList = rawgClient.getGamesList(1);
        System.out.println(fetchedGamesList);

        //Then
        assertEquals(1, fetchedGamesList.size());
        assertTrue(fetchedGamesList.get(0).getResults().contains(gameFromList));
    }

    @Test
    void shouldReturnRawgGameDetailedById() throws URISyntaxException {
        //Given
        RawgGameDetailedDto gameToFindById = RawgGameDetailedDto.builder()
                .id(1L)
                .name("test_name")
                .description("test_description")
                .metacritic(90.0)
                .tba(false)
                .released(LocalDate.now())
                .officialWebsiteURL("test_url")
                .build();

        URI uri = new URI(TEST_URI + "games/1?key=" + TEST_KEY);

        when(restTemplate.getForObject(uri, RawgGameDetailedDto.class)).thenReturn(gameToFindById);

        //When
        RawgGameDetailedDto gameFoundById = rawgClient.getGameById(1L).orElseThrow();

        //Then
        assertEquals(1L, gameFoundById.getId());
        assertEquals("test_name", gameFoundById.getName());
        assertEquals("test_description", gameFoundById.getDescription());
        assertEquals(90.0, gameFoundById.getMetacritic());
    }
}