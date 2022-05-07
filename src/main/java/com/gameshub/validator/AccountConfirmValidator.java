package com.gameshub.validator;

import com.gameshub.domain.user.User;
import com.gameshub.exceptions.UserNotFoundException;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountConfirmValidator {

    private final UserRepository userRepository;

    public boolean validate(final User user) throws UserNotFoundException {
        return userRepository
                .findByLoginName(user.getLoginName())
                .orElseThrow(UserNotFoundException::new)
                .isVerified();
    }
}
