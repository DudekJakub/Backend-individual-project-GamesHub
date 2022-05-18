package com.gameshub.service;

import com.gameshub.domain.game.rawgGame.RawgGameDetailedDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RawgServiceTest {

    @Autowired
    private RawgService rawgService;

    @Test
    public void testMapNameToRawgsSlugName() throws NoSuchMethodException,
                                                    InvocationTargetException,
                                                    IllegalAccessException
    {
        //Given
        Method method = rawgService.getClass().getDeclaredMethod("mapNameToRawgsSlugName", String.class);
        method.setAccessible(true);

        //When
        String result = (String) method.invoke(rawgService, "The elder scroll V");

        //Then
        assertEquals("The-elder-scroll-V", result);
    }

    @Test
    public void testFetchListOfRawgGamesRelatedToGivenName() {
        //Given
        String givenName = "doom";

        //When
        List<RawgGameDetailedDto> resultGamesList = rawgService.fetchListOfRawgGamesRelatedToGivenName(givenName);
        System.out.println(resultGamesList);

        //Then
        assertEquals(6, resultGamesList.size());
    }
}