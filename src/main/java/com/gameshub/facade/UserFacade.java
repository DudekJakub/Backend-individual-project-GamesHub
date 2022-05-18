package com.gameshub.facade;

import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.User;
import com.gameshub.domain.user.UserCloseDto;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.exception.*;
import com.gameshub.mapper.user.UserMapper;
import com.gameshub.service.UserService;
import com.gameshub.validator.AccountConfirmValidator;
import com.gameshub.validator.EmailValidator;
import com.gameshub.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.gameshub.facade.OperationName.*;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

    private final UserService userService;
    private final UserMapper userMapper;

    private final UserValidator userValidator;
    private final EmailValidator emailValidator;
    private final AccountConfirmValidator accountConfirmValidator;

    public void signUpUser(final User user) throws UserLoginNameAlreadyExistsInDatabaseException,
                                                   UserEmailAlreadyExistsInDatabaseException {
        String userEmail = user.getEmail();

        LOGGER.info("Signing new User operation started...");
        userValidator.validateUserLoginNameDatabasePresence(user, SIGN_UP_USER);
        emailValidator.validateIfEmailAlreadyExistInDatabase(userEmail, SIGN_UP_USER);

        userService.signUpUser(user);
        LOGGER.info("New User signed up successfully!");
    }

    public String setUserNotificationStrategy(final Long userId, final String notificationStrategy) throws UserNotFoundException, UserNotVerifiedException {
        String operationDoneSuccessfully = "User's notification strategy switched successfully!";

        LOGGER.info("Switching User's notification strategy...");
        accountConfirmValidator.validateByUserId(userId, SWITCH_USER_NOTIFICATION_STRATEGY);

        userService.setUserNotificationStrategy(userId, notificationStrategy);
        LOGGER.info(operationDoneSuccessfully);

        return operationDoneSuccessfully;
    }

    public List<UserOpenDto> fetchUsersListForRegularUser() throws UserNotVerifiedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userLoginName = authentication.getName();
        accountConfirmValidator.validateByUserLoginName(userLoginName, FETCH_USERS_LIST_FOR_REGULAR_USER);

        return userMapper.mapToUserOpenDtoList(userService.getAllUsers());
    }

    public List<UserCloseDto> fetchUsersListForAdmin() throws UserNotFoundException, UserIsNotAdminException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userLoginName = authentication.getName();
        userValidator.validateByLoginNameIfUserHasAdminRole(userLoginName, FETCH_USERS_LIST_FOR_ADMIN);

        return userMapper.mapToUserCloseDtoList(userService.getAllUsers());
    }
}
