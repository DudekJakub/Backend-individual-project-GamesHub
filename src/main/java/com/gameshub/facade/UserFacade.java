package com.gameshub.facade;

import com.gameshub.domain.book.BookDto;
import com.gameshub.domain.game.GameDto;
import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.domain.game.GameRatingDto;
import com.gameshub.domain.user.User;
import com.gameshub.domain.user.UserCloseDto;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.exception.*;
import com.gameshub.mapper.book.BookMapper;
import com.gameshub.mapper.game.GameMapper;
import com.gameshub.mapper.game.GameOpinionMapper;
import com.gameshub.mapper.game.GameRatingMapper;
import com.gameshub.mapper.user.UserMapper;
import com.gameshub.repository.UserRepository;
import com.gameshub.service.UserService;
import com.gameshub.validator.EmailValidator;
import com.gameshub.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.gameshub.facade.OperationName.*;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final GameMapper gameMapper;
    private final BookMapper bookMapper;
    private final GameOpinionMapper gameOpinionMapper;
    private final GameRatingMapper gameRatingMapper;

    private final UserValidator userValidator;
    private final EmailValidator emailValidator;

    public void signUpUser(final User user) throws UserLoginNameAlreadyExistsInDatabaseException,
                                                   UserEmailAlreadyExistsInDatabaseException {
        String userEmail = user.getEmail();

        LOGGER.info("Signing new User operation started...");

        userValidator.validateUserLoginNameDatabaseAbsence(user, SIGN_UP_USER);
        emailValidator.validateEmailDatabaseAbsence(userEmail, SIGN_UP_USER);
        userService.signUpUser(user);

        LOGGER.info("New User signed up successfully!");
    }

    public String setUserNotificationStrategy(final Long userId, final String notificationStrategy) throws UserNotFoundException {
        String operationDoneSuccessfully = "User's notification strategy switched successfully!";

        LOGGER.info("Switching User's notification strategy...");
        userService.setUserNotificationStrategy(userId, notificationStrategy);
        LOGGER.info(operationDoneSuccessfully);

        return operationDoneSuccessfully;
    }

    public Set<BookDto> fetchUserMemorizedBooks(final Long userId) throws UserNotFoundException {
        return bookMapper.mapToBookListDtos(userService.getUserMemorizedBooks(userId));
    }

    public Set<GameDto> fetchUserObservedGames(final Long userId) throws UserNotFoundException {
        return gameMapper.mapToGameSetDto(userService.getUserObservedGames(userId));
    }

    public List<GameOpinionDto> fetchUserGamesOpinions(final Long userId) throws UserNotFoundException {
        return gameOpinionMapper.mapToListOfGameOpinionDtos(userService.getUserGamesOpinions(userId));
    }

    public List<GameRatingDto> fetchUserGamesRatings(final Long userId) throws UserNotFoundException {
        return gameRatingMapper.mapToListOfGameRatingDtos(userService.getUserGamesRatings(userId));
    }

    public List<UserOpenDto> fetchUsersListForRegularUser() {
        return userMapper.mapToUserOpenDtoList(userService.getAllUsers());
    }

    public List<UserCloseDto> fetchUsersListForAdmin() {
        return userMapper.mapToUserCloseDtoList(userService.getAllUsers());
    }

    public UserCloseDto retrieveUserInfoForUserItselfAndAdmin(final Long userId) throws UserNotFoundException, AccessDeniedException {
        User userForInfo = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        userValidator.validateUserAccessToData(userId, RETRIEVE_USER_INFO_FOR_USER_ITSELF_AND_ADMIN);

        return userMapper.mapToUserCloseDto(userForInfo);
    }

    public UserOpenDto retrieveUserInfoForRegularUser(final Long userId) throws UserNotFoundException {
        User userForInfo = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return userMapper.mapToUserOpenDto(userForInfo);
    }
}
