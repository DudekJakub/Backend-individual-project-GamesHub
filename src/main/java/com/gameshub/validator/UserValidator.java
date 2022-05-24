package com.gameshub.validator;

import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.AccessDeniedException;
import com.gameshub.exception.UserLoginNameAlreadyExistsInDatabaseException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.exception.UserNotVerifiedException;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidator.class);

    private final UserRepository userRepository;

    public void validateUserLoginNameDatabaseAbsence(final User user, final String operationName) throws UserLoginNameAlreadyExistsInDatabaseException {
        boolean loginNameAlreadyExists = userRepository.findByLoginName(user.getLoginName()).isPresent();

        if (loginNameAlreadyExists) {
            LOGGER.warn(operationName + "Validation failed! Given login name already exists in database!");
            throw new UserLoginNameAlreadyExistsInDatabaseException();
        }
    }

    public void validateUserAccessToData(final Long userId, final String operationName) throws UserNotFoundException, AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedUserName = authentication.getName();

        User currentLoggedUser = userRepository.findByLoginName(currentLoggedUserName).orElseThrow(UserNotFoundException::new);

        if(!currentLoggedUser.getId().equals(userId) && !currentLoggedUser.getAppUserRole().equals(AppUserRole.ADMIN)) {
            LOGGER.warn(operationName + "Validation failed! Current logged user has no access to required data!");
            throw new AccessDeniedException();
        }
    }

    public boolean validateByUserLoginName(final String userLoginName) throws UserNotVerifiedException {
        boolean isUserVerified = userRepository
                .findByLoginName(userLoginName)
                .orElseThrow(UserNotVerifiedException::new)
                .isVerified();
        if (!isUserVerified) {
            LOGGER.warn("Validation failed! User is not verified via e-mail!");
            throw new UserNotVerifiedException();
        }
        return true;
    }

    public boolean hasValidatedAccount() throws UserNotVerifiedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedUserName = authentication.getName();

        return validateByUserLoginName(currentLoggedUserName);
    }
}
