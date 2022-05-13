package com.gameshub.rawg.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RawgConfigTest {

    @Autowired
    private RawgConfig rawgConfig;

    @Test
    void getRawgApiEndpoint() {
        //Given & When
        var rawgApiEndpoint = rawgConfig.getRawgApiEndpoint();

        //Then
        assertEquals("https://api.rawg.io/api/", rawgApiEndpoint);
    }

    @Test
    void getRawgAppKey() {
        //Given & When
        var rawgAppKey = rawgConfig.getRawgAppKey();

        //Then
        assertEquals("7619ad3c89dd42838b84961b780df5eb", rawgAppKey);
    }
}