package com.gameshub.service;

import com.gameshub.domain.game.rawgGame.fromList.RawgGameFromListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RawgServiceTest {

    @Autowired
    private RawgService rawgService;

    @Test
    void fillDatabase() {
        rawgService.fillDatabaseWithGames();
    }
}