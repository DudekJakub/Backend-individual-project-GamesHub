package com.gameshub.validator;

import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.UserIsNotAdminException;
import com.gameshub.exception.UserLoginNameAlreadyExistsInDatabaseException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidator.class);

    private final UserRepository userRepository;

    public void validateUserLoginNameDatabasePresence(final User user, final String operationName) throws UserLoginNameAlreadyExistsInDatabaseException {
        boolean loginNameAlreadyExists = userRepository.findByLoginName(user.getLoginName()).isPresent();

        if (loginNameAlreadyExists) {
            LOGGER.error(operationName + "Validation failed! Given login name already exists in database!");
            throw new UserLoginNameAlreadyExistsInDatabaseException();
        }
    }

    public void validateByLoginNameIfUserHasAdminRole(final String userLoginName, final String operationName) throws UserNotFoundException, UserIsNotAdminException {
        User userFromDatabase = userRepository.findByLoginName(userLoginName).orElseThrow(UserNotFoundException::new);
        Enum<AppUserRole> userRole = userFromDatabase.getAppUserRole();

        if (!userRole.equals(AppUserRole.ADMIN)) {
            LOGGER.error(operationName + "Validation failed! Given user has NOT - ADMIN - role!");
            throw new UserIsNotAdminException();
        }
    }
}
