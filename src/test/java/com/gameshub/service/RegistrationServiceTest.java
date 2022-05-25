package com.gameshub.service;

import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.RegistrationRequestDto;
import com.gameshub.domain.user.User;
import com.gameshub.exception.*;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RegistrationServiceTest {

    @Autowired
    private RegistrationService service;

    @Autowired
    private UserRepository userRepository;

    @Test
    void register() throws PasswordNotMatchException, UserEmailAlreadyExistsInDatabaseException, EmailAddressNotExistsException, EmailVerificationFailedException, UserLoginNameAlreadyExistsInDatabaseException {
        //Given
        User userForTest = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .email("jakub.dudek94@gmail.com")
                .password("pass")
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .firstname("firstname")
                .lastname("lastname")
                .build();

        RegistrationRequestDto request = RegistrationRequestDto.builder()
                .loginName("test")
                .email("jakub.dudek94@gmail.com")
                .firstname("firstname")
                .lastname("lastname")
                .password("pass")
                .repeatPassword("pass")
                .build();

        //When
        String result = service.register(request);

        //Then
        User registeredUser = userRepository.findByLoginName("test").orElseThrow();

        assertNotNull(result);
        assertEquals(1, userRepository.findAll().size());
        assertEquals(userForTest.getEmail(), registeredUser.getEmail());
        assertEquals(userForTest.getLoginName(), registeredUser.getLoginName());
        assertEquals(userForTest.getNotificationStrategy(), registeredUser.getNotificationStrategy());
        assertEquals(userForTest.getAppUserRole(), registeredUser.getAppUserRole());
    }

    @Test
    void confirmAccount() throws UserAlreadyVerifiedException {
        //Given
        User userForTest = User.builder()
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .email("email")
                .password("pass")
                .firstname("firstname")
                .lastname("lastname")
                .build();

        userRepository.save(userForTest);

        //When
        String result = service.confirmAccount(userForTest);

        //Then
        User userFromDatabase = userRepository.findByLoginName("test").orElseThrow();

        assertNotNull(result);
        assertNotNull(userFromDatabase);
        assertTrue(userFromDatabase.isVerified());
    }
}