package com.gameshub.controller;

import com.gameshub.domain.notification.AppNewOpinionNotifDto;
import com.gameshub.domain.notification.AppNewRatingNotifDto;
import com.gameshub.exception.AppNotifNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.service.AppNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notifications")
public class AppNotificationController {

    private final AppNotificationService appNotificationService;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/opinions/{userId}")
    public ResponseEntity<Set<AppNewOpinionNotifDto>> userNewOpinionNotif(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(appNotificationService.getUserNewOpinionNotifs(userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/ratings/{userId}")
    public ResponseEntity<Set<AppNewRatingNotifDto>> userNewRatingNotif(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(appNotificationService.getUserNewRatingNotifs(userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @DeleteMapping("/{notifId}/{userId}")
    public ResponseEntity<Boolean> userNotif(@PathVariable Long notifId, @PathVariable Long userId) throws UserNotFoundException, AppNotifNotFoundException {
        return ResponseEntity.ok(appNotificationService.deleteNotif(notifId, userId));
    }
}
