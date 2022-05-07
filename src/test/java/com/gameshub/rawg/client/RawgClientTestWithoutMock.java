package com.gameshub.rawg.client;

import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;

@SpringBootTest
public class RawgClientTestWithoutMock {

    @Autowired
    private RawgClient rawgClient;

    @Test
    void shouldReturnRawgGameDetailedByName() {
        //Given
        String gameName = "fallout-4";

        //When
        RawgGameDetailedDto result = rawgClient.getGameByName(gameName).get();

        //Then
        System.out.println(result);
    }
}
