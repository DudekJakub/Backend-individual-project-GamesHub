package com.gameshub.service;

import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private User userForTest;

    @BeforeEach
    void setSettings() {
        userForTest = User.builder()
                .id(1L)
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.FULL_EMAIL_NOTIFICATION)
                .email("test_email")
                .password("test_password")
                .firstname("test_firstname")
                .lastname("test_lastname")
                .active(false)
                .verified(true)
                .build();
    }

    @Test
    void loadUserByUsername() {
        //Given
        String loginName = "test";

        when(userRepository.findByLoginName(loginName)).thenReturn(Optional.ofNullable(userForTest));

        //When
        UserDetails result = userService.loadUserByUsername(loginName);

        //Then
        assertNotNull(result);
        assertEquals(userForTest.getLoginName(), result.getUsername());
        assertEquals(userForTest.getPassword(), result.getPassword());
    }

    @Test
    void signUpUser() {
        //Given
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(userForTest)).thenReturn(userForTest);

        //When
        userService.signUpUser(userForTest);

        //Then
        assertTrue(userForTest.isActive());
        assertEquals("encoded_password", userForTest.getPassword());
    }

    @Test
    void setUserNotificationStrategy() throws UserNotFoundException {
        //Given
        String notificationStrategy = "app";

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userForTest));

        //When
        userService.setUserNotificationStrategy(1L, notificationStrategy);

        //Then
        assertEquals(AppUserNotificationStrategy.INSIDE_APP_NOTIFICATION, userForTest.getNotificationStrategy());
    }
}