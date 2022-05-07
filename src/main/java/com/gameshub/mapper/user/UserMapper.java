package com.gameshub.mapper.user;

import com.gameshub.domain.user.User;
import com.gameshub.domain.user.UserCloseDto;
import com.gameshub.domain.user.UserOpenDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public User mapToUserFromCloseDto(final UserCloseDto userCloseDto) {
        return User.builder()
                .id(userCloseDto.getId())
                .loginName(userCloseDto.getLoginName())
                .firstname(userCloseDto.getFirstname())
                .lastname(userCloseDto.getLastname())
                .email(userCloseDto.getEmail())
                .appUserRole(userCloseDto.getAppUserRole())
                .active(userCloseDto.isActive())
                .verified(userCloseDto.isVerified())
                .build();
    }

    public User mapToUserFromOpenDto(final UserOpenDto userOpenDto) {
        return User.builder()
                .firstname(userOpenDto.getFirstname())
                .lastname(userOpenDto.getLastname())
                .build();
    }

    public UserCloseDto mapToUserCloseDto(final User user) {
        return UserCloseDto.builder()
                .id(user.getId())
                .loginName(user.getLoginName())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .appUserRole(user.getAppUserRole())
                .registeredDate(user.getRegisteredDate())
                .active(user.isActive())
                .verified(user.isVerified())
                .build();
    }

    public UserOpenDto mapToUserOpenDto(final User user) {
        return UserOpenDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .registeredDate(user.getRegisteredDate())
                .email(user.getEmail())
                .build();
    }

    public List<UserCloseDto> mapToUserCloseDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserCloseDto)
                .collect(Collectors.toList());
    }

    public List<UserOpenDto> mapToUserOpenDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserOpenDto)
                .collect(Collectors.toList());
    }

    public List<User> mapToUserListFromCloseDto(final List<UserCloseDto> userCloseDtos) {
        return userCloseDtos.stream()
                .map(this::mapToUserFromCloseDto)
                .collect(Collectors.toList());
    }

    public List<User> mapToUserListFromOpenDto(final List<UserOpenDto> userOpenDtos) {
        return userOpenDtos.stream()
                .map(this::mapToUserFromOpenDto)
                .collect(Collectors.toList());
    }
}
