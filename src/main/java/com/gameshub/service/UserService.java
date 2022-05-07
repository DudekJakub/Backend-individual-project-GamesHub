package com.gameshub.service;

import com.gameshub.domain.user.AppUserDetails;
import com.gameshub.domain.user.User;
import com.gameshub.exceptions.UserEmailAlreadyExistsInDatabaseException;
import com.gameshub.exceptions.UserLoginNameAlreadyExistsInDatabaseException;
import com.gameshub.exceptions.UserNotFoundException;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLoginName(loginName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + loginName));

        return user.map(AppUserDetails::new).get();
    }

    @Transactional
    public void signUpUser(final User user) throws UserLoginNameAlreadyExistsInDatabaseException,
                                                 UserEmailAlreadyExistsInDatabaseException {
        boolean loginNameAlreadyExists = userRepository
                .findByLoginName(user.getLoginName())
                .isPresent();

        boolean emailAlreadyExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if (loginNameAlreadyExists) throw new UserLoginNameAlreadyExistsInDatabaseException();
        if (emailAlreadyExists) throw new UserEmailAlreadyExistsInDatabaseException();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByLoginName(String loginName) throws UserNotFoundException {
        return userRepository.findByLoginName(loginName).orElseThrow(UserNotFoundException::new);
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
