package com.gameshub.repository;

import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findById() throws UserNotFoundException {
        //Given
        Long userId = 5L;

        //When
        User userFromDatabase = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        //Then
        assertNotNull(userFromDatabase);
        assertEquals("admin", userFromDatabase.getLoginName());
        assertEquals(5L, userFromDatabase.getId());
        assertEquals("jakubjavaprogrammer@gmail.com", userFromDatabase.getEmail());
        assertEquals(AppUserRole.ADMIN, userFromDatabase.getAppUserRole());
    }

    @Test
    void findByEmail() throws UserNotFoundException {
        //Given
        String email = "jakubjavaprogrammer@gmail.com";

        //When
        User userFromDatabase = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        //Then
        assertNotNull(userFromDatabase);
        assertEquals("admin", userFromDatabase.getLoginName());
        assertEquals(email, userFromDatabase.getEmail());
        assertEquals(AppUserRole.ADMIN, userFromDatabase.getAppUserRole());
    }

    @Test
    void findByLoginName() throws UserNotFoundException {
        //Given
        String loginName = "admin";

        //When
        User userFromDatabase = userRepository.findByLoginName(loginName).orElseThrow(UserNotFoundException::new);

        //Then
        assertNotNull(userFromDatabase);
        assertEquals("admin", userFromDatabase.getLoginName());
        assertEquals(AppUserRole.ADMIN, userFromDatabase.getAppUserRole());
    }

    @Test
    void findAll() throws UserNotFoundException {
        //Given
        User admin = userRepository.findByLoginName("admin").orElseThrow(UserNotFoundException::new);

        // When
        List<User> usersFromDatabase = userRepository.findAll();
        var adminFromDatabase = usersFromDatabase.get(1);

        /* Test of User's equals & hashCode contract: **/
        System.out.println("admin hashcode = " + admin.hashCode()
                           + " | usersFromDatabase.get(1) hashcode = " + adminFromDatabase.hashCode()
                           + " -> are hashCodes equal = " + admin.equals(adminFromDatabase));

        //Then
        assertNotNull(usersFromDatabase);
        assertTrue(usersFromDatabase.size() > 0);
        assertTrue(usersFromDatabase.contains(admin));
    }
}
