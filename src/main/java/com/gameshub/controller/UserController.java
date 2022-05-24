package com.gameshub.controller;

import com.gameshub.domain.book.BookDto;
import com.gameshub.domain.game.GameDto;
import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.domain.game.GameRatingDto;
import com.gameshub.domain.user.UserCloseDto;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.exception.AccessDeniedException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserFacade facade;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/restricted/{userId}")
    public ResponseEntity<UserCloseDto> userRestrictedInfo(@PathVariable Long userId) throws UserNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(facade.retrieveUserInfoForUserItselfAndAdmin(userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/opened/{userId}")
    public ResponseEntity<UserOpenDto> userOpenedInfo(@PathVariable Long userId) throws UserNotFoundException{
        return ResponseEntity.ok(facade.retrieveUserInfoForRegularUser(userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping()
    public ResponseEntity<List<UserOpenDto>> users() {
        return ResponseEntity.ok(facade.fetchUsersListForRegularUser());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<List<UserCloseDto>> usersForAdmin() {
        return ResponseEntity.ok(facade.fetchUsersListForAdmin());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @PutMapping("/setNotificationStrategy/{userId}")
    public ResponseEntity<String> updateUserNotificationStrategy(@PathVariable Long userId, @RequestParam String notificationStrategy) throws UserNotFoundException {
        return ResponseEntity.ok(facade.setUserNotificationStrategy(userId, notificationStrategy));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/books/{userId}")
    public ResponseEntity<Set<BookDto>> userMemorizedBooks(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(facade.fetchUserMemorizedBooks(userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/gamesObserved/{userId}")
    public ResponseEntity<Set<GameDto>> userObservedGames(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(facade.fetchUserObservedGames(userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/gamesOpinions/{userId}")
    public ResponseEntity<List<GameOpinionDto>> userGamesOpinions(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(facade.fetchUserGamesOpinions(userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/gamesRatings/{userId}")
    public ResponseEntity<List<GameRatingDto>> userGamesRatings(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(facade.fetchUserGamesRatings(userId));
    }
}
