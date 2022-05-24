package com.gameshub.mapper.user;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.user.User;
import com.gameshub.domain.user.UserCloseDto;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository repository;

    public User mapToUserFromId(final Long userId) throws UserNotFoundException {
        return repository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserCloseDto mapToUserCloseDto(final User user) {
        return UserCloseDto.builder()
                .id(user.getId())
                .loginName(user.getLoginName())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .appUserRole(user.getAppUserRole())
                .notificationStrategy(user.getNotificationStrategy())
                .registeredDate(user.getRegisteredDate())
                .active(user.isActive())
                .verified(user.isVerified())
                .opinionsQnt(user.getOpinionsQnt())
                .opinionsPerDay(user.getOpinionsPerDay())
                .ratingsQnt(user.getRatingsQnt())
                .ratingsPerDay(user.getRatingsPerDay())
                .gameOpinionsIds(user.getGameOpinions().stream().map(GameOpinion::getId).collect(Collectors.toList()))
                .gameRatingsIds(user.getGameRatings().stream().map(GameRating::getId).collect(Collectors.toList()))
                .gamesOwnedIds(user.getGamesOwned().stream().map(Game::getId).collect(Collectors.toSet()))
                .gamesWantedIds(user.getGamesWantedToOwn().stream().map(Game::getId).collect(Collectors.toSet()))
                .gamesObservedIds(user.getObservedGames().stream().map(Game::getId).collect(Collectors.toSet()))
                .build();
    }

    public UserOpenDto mapToUserOpenDto(final User user) {
        return UserOpenDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .registeredDate(user.getRegisteredDate())
                .email(user.getEmail())
                .opinionsQnt(user.getOpinionsQnt())
                .opinionsPerDay(user.getOpinionsPerDay())
                .ratingsQnt(user.getRatingsQnt())
                .ratingsPerDay(user.getRatingsPerDay())
                .gameOpinionsIds(user.getGameOpinions().stream().map(GameOpinion::getId).collect(Collectors.toList()))
                .gameRatingsIds(user.getGameRatings().stream().map(GameRating::getId).collect(Collectors.toList()))
                .gamesOwnedIds(user.getGamesOwned().stream().map(Game::getId).collect(Collectors.toSet()))
                .gamesWantedIds(user.getGamesWantedToOwn().stream().map(Game::getId).collect(Collectors.toSet()))
                .gamesObservedIds(user.getObservedGames().stream().map(Game::getId).collect(Collectors.toSet()))
                .build();
    }

    public List<UserCloseDto> mapToUserCloseDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserCloseDto)
                .collect(Collectors.toList());
    }

    public List<UserOpenDto> mapToUserOpenDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserOpenDto)
                .collect(Collectors.toList());
    }

    public Set<UserOpenDto> mapToUserOpenDtoSet(final Set<User> users) {
        return users.stream()
                .map(this::mapToUserOpenDto)
                .collect(Collectors.toSet());
    }
}
