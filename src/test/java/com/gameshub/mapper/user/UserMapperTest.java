package com.gameshub.mapper.user;

import com.gameshub.domain.user.*;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Test
    void mapToUserFromId() throws UserNotFoundException {
        //Given
        User user = User.builder()
                .id(1L)
                .firstname("name")
                .lastname("lastName")
                .loginName("loginName")
                .email("email")
                .password("pass")
                .verified(true)
                .active(true)
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        //When
        User mappedUser = userMapper.mapToUserFromId(1L);

        //Then
        assert user != null;
        assertEquals(user.getId(), mappedUser.getId());
        assertEquals(user.getFirstname(), mappedUser.getFirstname());
        assertEquals(user.getLastname(), mappedUser.getLastname());
        assertEquals(user.getLoginName(), mappedUser.getLoginName());
        assertEquals(user.isVerified(), mappedUser.isVerified());
        assertEquals(user.getAppUserRole(), mappedUser.getAppUserRole());
        assertEquals(user.getNotificationStrategy(), mappedUser.getNotificationStrategy());
    }

    @Test
    void mapToUserCloseDto() {
        //Given
        User user = User.builder()
                .id(1L)
                .firstname("name")
                .lastname("lastName")
                .loginName("loginName")
                .email("email")
                .password("pass")
                .verified(true)
                .active(true)
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .build();

        //When
        UserCloseDto mappedUser = userMapper.mapToUserCloseDto(user);

        //Then
        assertEquals(user.getId(), mappedUser.getId());
        assertEquals(user.getFirstname(), mappedUser.getFirstname());
        assertEquals(user.getLastname(), mappedUser.getLastname());
        assertEquals(user.getLoginName(), mappedUser.getLoginName());
        assertEquals(user.isVerified(), mappedUser.isVerified());
        assertEquals(user.getAppUserRole(), mappedUser.getAppUserRole());
        assertEquals(user.getNotificationStrategy(), mappedUser.getNotificationStrategy());
    }

    @Test
    void mapToUserOpenDto() {
        //Given
        User user = User.builder()
                .id(1L)
                .firstname("name")
                .lastname("lastName")
                .loginName("loginName")
                .email("email")
                .password("pass")
                .verified(true)
                .active(true)
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .build();

        //When
        UserOpenDto mappedUser = userMapper.mapToUserOpenDto(user);

        //Then
        assertEquals(user.getFirstname(), mappedUser.getFirstname());
        assertEquals(user.getLastname(), mappedUser.getLastname());
        assertEquals(user.getEmail(), mappedUser.getEmail());
    }

    @Test
    void mapToUserCloseDtoList() {
        //Given
        List<User> users = List.of(
               User.builder()
                        .id(1L)
                        .firstname("name")
                        .lastname("lastName")
                        .loginName("loginName")
                        .email("email")
                        .password("pass")
                        .verified(true)
                        .active(true)
                        .appUserRole(AppUserRole.USER)
                        .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                        .build());

        //When
        List<UserCloseDto> resultList = userMapper.mapToUserCloseDtoList(users);

        //Then
        assertEquals(users.get(0).getId(), resultList.get(0).getId());
        assertEquals(users.get(0).getFirstname(), resultList.get(0).getFirstname());
        assertEquals(users.get(0).getLastname(), resultList.get(0).getLastname());
        assertEquals(users.get(0).getLoginName(), resultList.get(0).getLoginName());
        assertEquals(users.get(0).isVerified(), resultList.get(0).isVerified());
        assertEquals(users.get(0).getAppUserRole(), resultList.get(0).getAppUserRole());
        assertEquals(users.get(0).getNotificationStrategy(), resultList.get(0).getNotificationStrategy());
    }

    @Test
    void mapToUserOpenDtoList() {
        //Given
        List<User> users = List.of(
                User.builder()
                        .id(1L)
                        .firstname("name")
                        .lastname("lastName")
                        .loginName("loginName")
                        .email("email")
                        .password("pass")
                        .verified(true)
                        .active(true)
                        .appUserRole(AppUserRole.USER)
                        .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                        .build());

        //When
        List<UserOpenDto> resultList = userMapper.mapToUserOpenDtoList(users);

        //Then
        assertEquals(users.get(0).getEmail(), resultList.get(0).getEmail());
        assertEquals(users.get(0).getFirstname(), resultList.get(0).getFirstname());
        assertEquals(users.get(0).getLastname(), resultList.get(0).getLastname());
    }

    @Test
    void mapToUserOpenDtoSet() {
        //Given
        Set<User> users = Set.of(
                User.builder()
                        .id(1L)
                        .firstname("name")
                        .lastname("lastName")
                        .loginName("loginName")
                        .email("email")
                        .password("pass")
                        .verified(true)
                        .active(true)
                        .appUserRole(AppUserRole.USER)
                        .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                        .build());

        //When
        Set<UserOpenDto> resultList = userMapper.mapToUserOpenDtoSet(users);

        //Then
        User fromUsers = users.stream().findAny().orElseThrow();
        UserOpenDto fromResultList = resultList.stream().findAny().orElseThrow();

        assertEquals(users.size(), resultList.size());
        assertEquals(fromUsers.getFirstname(), fromResultList.getFirstname());
        assertEquals(fromUsers.getLastname(), fromResultList.getLastname());
        assertEquals(fromUsers.getEmail(), fromResultList.getEmail());
    }
}