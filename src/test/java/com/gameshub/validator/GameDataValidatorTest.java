package com.gameshub.validator;

import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameDataValidatorTest {

    @Autowired
    private UserRepository userRepository;
    private final GameDataValidator gameDataValidator = new GameDataValidator(userRepository);


}