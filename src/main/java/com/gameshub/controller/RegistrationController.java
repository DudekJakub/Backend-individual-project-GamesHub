package com.gameshub.controller;

import com.gameshub.domain.user.RegistrationRequestDto;
import com.gameshub.exception.*;
import com.gameshub.service.RegistrationService;
import com.gameshub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody RegistrationRequestDto request) throws UserEmailAlreadyExistsInDatabaseException, UserLoginNameAlreadyExistsInDatabaseException,
                                                                                               PasswordNotMatchException, EmailAddressNotExistsException, EmailVerificationFailedException {
        return ResponseEntity.ok(registrationService.register(request));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/confirm/{loginName}")
    public ResponseEntity<String> confirm(@PathVariable String loginName) throws UserNotFoundException, UserAlreadyVerifiedException {
        return ResponseEntity.ok(registrationService.confirmAccount(userService.getUserByLoginName(loginName)));
    }
}
