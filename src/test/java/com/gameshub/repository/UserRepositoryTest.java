package com.gameshub.repository;

import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void save() {
        //Given
        User user = User.builder()
                        .firstname("test_name")
                        .lastname("test_lastName")
                        .email("test_email")
                        .loginName("test_loginName")
                        .appUserRole(AppUserRole.USER)
                        .build();

        //When
        User savedUser = userRepository.save(user);

        //Then
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("test_name", savedUser.getFirstname());
        assertEquals("test_lastName", savedUser.getLastname());
        assertEquals("test_email", savedUser.getEmail());
        assertEquals("test_loginName", savedUser.getLoginName());
    }

    @Test
    @Transactional
    void findAll() {
        //Given
        userRepository.findAll().clear();

        User user = User.builder()
                .firstname("test_name")
                .lastname("test_lastName")
                .email("test_email")
                .loginName("test_loginName")
                .appUserRole(AppUserRole.USER)
                .build();

        User user2 = User.builder()
                .firstname("test_name2")
                .lastname("test_lastName2")
                .email("test_email2")
                .loginName("test_loginName2")
                .appUserRole(AppUserRole.USER)
                .build();

        userRepository.save(user);

        //When
        List<User> users = userRepository.findAll();

        //Then
        assertEquals(2, users.size());
        assertNotNull(users.get(0));
        assertNotNull(users.get(1));
        assertEquals(user.getId(), users.get(0).getId());
        assertEquals(user2.getId(), users.get(1).getId());
    }

    @Test
    @Transactional
    void findById() {
        //Given
        User user = User.builder()
                .firstname("test_name")
                .lastname("test_lastName")
                .email("test_email")
                .loginName("test_loginName")
                .appUserRole(AppUserRole.USER)
                .build();

        User savedUser = userRepository.save(user);

        //When
        User resultUser = userRepository.findById(savedUser.getId()).orElseThrow();

        //Then
        assertEquals(savedUser.getId(), resultUser.getId());
        assertEquals(savedUser.getFirstname(), resultUser.getFirstname());
    }

    @Test
    @Transactional
    void findByEmail() {
        //Given
        User user = User.builder()
                .firstname("test_name")
                .lastname("test_lastName")
                .email("test_email")
                .loginName("test_loginName")
                .appUserRole(AppUserRole.USER)
                .build();

        User savedUser = userRepository.save(user);

        //When
        User resultUser = userRepository.findByEmail(savedUser.getEmail()).orElseThrow();

        //Then
        assertEquals(savedUser.getEmail(), resultUser.getEmail());
    }

    @Test
    @Transactional
    void findByLoginName() {
        //Given
        User user = User.builder()
                .firstname("test_name")
                .lastname("test_lastName")
                .email("test_email")
                .loginName("test_loginName")
                .appUserRole(AppUserRole.USER)
                .build();

        User savedUser = userRepository.save(user);

        //When
        User resultUser = userRepository.findByLoginName(savedUser.getLoginName()).orElseThrow();

        //Then
        assertEquals(savedUser.getLoginName(), resultUser.getLoginName());
    }


}
