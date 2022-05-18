package com.gameshub.mapper.user;

import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.RegistrationRequestDto;
import com.gameshub.domain.user.User;

public class AppUserMapper {
    public static User mapToAppUser(final RegistrationRequestDto request) {
        return User.builder()
                .loginName(request.getLoginName())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(request.getPassword())
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .verified(false)
                .build();
    }
}
