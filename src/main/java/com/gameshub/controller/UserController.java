package com.gameshub.controller;

import com.gameshub.domain.user.UserCloseDto;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.exception.UserIsNotAdminException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.exception.UserNotVerifiedException;
import com.gameshub.facade.UserFacade;
import com.gameshub.mapper.user.UserMapper;
import com.gameshub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserFacade userFacade;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping()
    public ResponseEntity<List<UserOpenDto>> users() throws UserNotVerifiedException {
        return ResponseEntity.ok(userFacade.fetchUsersListForRegularUser());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<List<UserCloseDto>> usersForAdmin() throws UserNotFoundException, UserIsNotAdminException {
        return ResponseEntity.ok(userFacade.fetchUsersListForAdmin());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PutMapping("/setNotificationStrategy/{userId}")
    public ResponseEntity<String> updateUserNotificationStrategy(@PathVariable Long userId, @RequestParam String notificationStrategy) throws UserNotFoundException, UserNotVerifiedException {
        return ResponseEntity.ok(userFacade.setUserNotificationStrategy(userId, notificationStrategy));
    }
}
