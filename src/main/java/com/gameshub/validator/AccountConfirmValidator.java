package com.gameshub.validator;

import com.gameshub.domain.user.User;
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
public class AccountConfirmValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountConfirmValidator.class);

    private final UserRepository userRepository;

    public boolean validateByUser(final User user, final String operationName) throws UserNotFoundException, UserNotVerifiedException {
        boolean isUserVerified = userRepository
                                    .findByLoginName(user.getLoginName())
                                    .orElseThrow(UserNotFoundException::new)
                                    .isVerified();
        if (!isUserVerified) {
            LOGGER.error(operationName + "Validation failed! User is not verified via e-mail!");
            throw new UserNotVerifiedException();
        }
        return true;
    }

    public void validateByUserId(final Long userId, final String operationName) throws UserNotFoundException, UserNotVerifiedException {
        boolean isUserVerified = userRepository
                                    .findById(userId)
                                    .orElseThrow(UserNotFoundException::new)
                                    .isVerified();
        if (!isUserVerified) {
            LOGGER.error(operationName + "Validation failed! User is not verified via e-mail!");
            throw new UserNotVerifiedException();
        }
    }

    public void validateByUserLoginName(final String userLoginName, final String operationName) throws UserNotVerifiedException {
        boolean isUserVerified = userRepository
                                    .findByLoginName(userLoginName)
                                    .orElseThrow(UserNotVerifiedException::new)
                                    .isVerified();
        if (!isUserVerified) {
            LOGGER.error(operationName + "Validation failed! User is not verified via e-mail!");
            throw new UserNotVerifiedException();
        }
    }

    public void validateCurrentLoggedUser(final String operationName) throws UserNotVerifiedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedUserName = authentication.getName();

        validateByUserLoginName(currentLoggedUserName, operationName);
    }
}
