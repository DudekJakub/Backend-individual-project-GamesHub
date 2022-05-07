package com.gameshub.validator;

import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailValidator {

    private final UserRepository userRepository;

    public boolean validate(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
