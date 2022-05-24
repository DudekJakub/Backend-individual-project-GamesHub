package com.gameshub.service;

import com.gameshub.domain.book.Book;
import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.user.AppUserDetails;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.User;
import com.gameshub.exception.UserNotFoundException;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String loginName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLoginName(loginName);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginName));

        return user.map(AppUserDetails::new).get();
    }

    @Transactional
    public void signUpUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }

    public void saveUser(final User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByLoginName(final String loginName) throws UserNotFoundException {
        return userRepository.findByLoginName(loginName).orElseThrow(UserNotFoundException::new);
    }

    public void setUserNotificationStrategy(final Long userId, final String notificationStrategy) throws UserNotFoundException {
        User userFromDatabase = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        AppUserNotificationStrategy appUserNotificationStrategy = null;

        switch (notificationStrategy) {
            case "full" :
                appUserNotificationStrategy = AppUserNotificationStrategy.FULL_EMAIL_NOTIFICATION;
                break;
            case "simple" :
                appUserNotificationStrategy = AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION;
                break;
            case "app" :
                appUserNotificationStrategy = AppUserNotificationStrategy.INSIDE_APP_NOTIFICATION;
                break;
        }
        userFromDatabase.setNotificationStrategy(appUserNotificationStrategy);
        saveUser(userFromDatabase);
    }

    public Set<Book> getUserMemorizedBooks(final Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new)
                .getBooksMemorized();
    }

    public Set<Game> getUserObservedGames(final Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new)
                .getObservedGames();
    }

    public List<GameOpinion> getUserGamesOpinions(final Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new)
                .getGameOpinions();
    }

    public List<GameRating> getUserGamesRatings(final Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new)
                .getGameRatings();
    }
}
