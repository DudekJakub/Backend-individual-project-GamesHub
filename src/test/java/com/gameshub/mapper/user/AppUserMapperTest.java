package com.gameshub.mapper.user;

import com.gameshub.domain.user.RegistrationRequestDto;
import com.gameshub.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppUserMapperTest {

    @Test
    void mapToAppUser() {
        //Given
        RegistrationRequestDto requestDto = RegistrationRequestDto.builder()
                .firstname("name")
                .lastname("lastName")
                .loginName("loginName")
                .email("email")
                .password("pass")
                .repeatPassword("pass")
                .build();

        //When
        User mappedUser = AppUserMapper.mapToAppUser(requestDto);

        //Then
        assertEquals(requestDto.getFirstname(), mappedUser.getFirstname());
        assertEquals(requestDto.getLastname(), mappedUser.getLastname());
        assertEquals(requestDto.getEmail(), mappedUser.getEmail());
        assertEquals(requestDto.getPassword(), mappedUser.getPassword());
        assertEquals(requestDto.getLoginName(), mappedUser.getLoginName());
    }
}