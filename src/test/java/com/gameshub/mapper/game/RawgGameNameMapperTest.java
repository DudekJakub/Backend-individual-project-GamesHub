package com.gameshub.mapper.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RawgGameNameMapperTest {

    @Test
    void mapGameNameToRawgSlugName() {
        //Given
        String nameToMap = "Destiny 2";

        //When
        String slugName = RawgGameNameMapper.mapGameNameToRawgSlugName(nameToMap);

        //Then
        assertEquals("Destiny-2", slugName);
    }

    @Test
    void mapSlugNameToGameName() {
        //Given
        String nameToMap = "destiny-2";

        //When
        String normalName = RawgGameNameMapper.mapSlugNameToGameName(nameToMap);

        //Then
        assertEquals("Destiny 2", normalName);
    }
}