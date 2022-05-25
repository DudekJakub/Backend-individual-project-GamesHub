package com.gameshub.client.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RawgApiConfigTest {

    @Autowired
    private RawgApiConfig rawgApiConfig;

    @Test
    void getRawgApiEndpoint() {
        //Given & When
        var rawgApiEndpoint = rawgApiConfig.getRawgApiEndpoint();

        //Then
        assertEquals("https://api.rawg.io/api/", rawgApiEndpoint);
    }

    @Test
    void getRawgAppKey() {
        //Given & When
        var rawgAppKey = rawgApiConfig.getRawgAppKey();

        //Then
        assertEquals("7619ad3c89dd42838b84961b780df5eb", rawgAppKey);
    }
}