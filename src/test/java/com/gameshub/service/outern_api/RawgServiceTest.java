package com.gameshub.service.outern_api;

import com.gameshub.client.RawgClient;
import com.gameshub.domain.game.rawgGame.RawgGameDetailedDto;
import com.gameshub.exception.GameSearchNotFoundException;
import com.gameshub.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RawgServiceTest {

    @InjectMocks
    private RawgService rawgService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private RawgClient rawgClient;

    @Test
    void fetchListOfRawgGamesRelatedToGivenName() throws GameSearchNotFoundException {
        //Given
        String name = "Destiny-2";
        RawgGameDetailedDto game1 = RawgGameDetailedDto.builder().id(1L).name("name1").description("desc1").build();
        RawgGameDetailedDto game2 = RawgGameDetailedDto.builder().id(2L).name("name2").description("desc2").build();

        when(gameRepository.retrieveGamesWhereNameIsLike(name)).thenReturn(List.of(1L,2L));
        when(rawgClient.getGameById(1L)).thenReturn(Optional.ofNullable(game1));
        when(rawgClient.getGameById(2L)).thenReturn(Optional.ofNullable(game2));

        //When
        List<RawgGameDetailedDto> resultList = rawgService.fetchListOfRawgGamesRelatedToGivenName(name);

        //Then
        assertNotNull(game1);
        assertNotNull(game2);
        assertEquals(2, resultList.size());
        assertEquals(game1.getId(), resultList.get(0).getId());
        assertEquals(game1.getName(), resultList.get(0).getName());
        assertEquals(game1.getDescription(), resultList.get(0).getDescription());
        assertEquals(game2.getId(), resultList.get(1).getId());
        assertEquals(game2.getName(), resultList.get(1).getName());
        assertEquals(game2.getDescription(), resultList.get(1).getDescription());
    }

    @Test
    void getRawgGameDetailedById() {
        //Given
        Long id = 1L;

        RawgGameDetailedDto game1 = RawgGameDetailedDto.builder().id(1L).name("name1").description("desc1").build();

        when(rawgClient.getGameById(id)).thenReturn(Optional.ofNullable(game1));

        //When
        RawgGameDetailedDto result = rawgService.getRawgGameDetailedById(id);

        //Then
        assertNotNull(result);
        assertNotNull(game1);
        assertEquals(game1.getId(), result.getId());
        assertEquals(game1.getName(), result.getName());
        assertEquals(game1.getDescription(), result.getDescription());
    }
}