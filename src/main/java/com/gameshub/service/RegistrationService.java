package com.gameshub.service;

import com.gameshub.domain.mail.Mail;
import com.gameshub.domain.user.RegistrationRequestDto;
import com.gameshub.domain.user.User;
import com.gameshub.email.EmailUserService;
import com.gameshub.email.builder.EmailBuilder;
import com.gameshub.exception.*;
import com.gameshub.facade.UserFacade;
import com.gameshub.mapper.user.AppUserMapper;
import com.gameshub.validator.EmailValidator;
import com.gameshub.validator.PasswordEqualityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private static final String EMAIL_WITH_LINK_JUST_SEND = "Email with confirmation request has been sent!";
    private static final String ACCOUNT_VERIFIED = "Account has been verified!";
    private static final String REGISTER_OPERATION = "[REGISTER OPERATION] ";

    private final EmailUserService emailUserService;
    private final UserFacade userFacade;
    private final UserService userService;

    private final EmailValidator emailValidator;
    private final PasswordEqualityValidator passwordEqualityValidator;

    public String register(final RegistrationRequestDto request) throws UserEmailAlreadyExistsInDatabaseException, UserLoginNameAlreadyExistsInDatabaseException,
                                                                        PasswordNotMatchException, EmailAddressNotExistsException, EmailVerificationFailedException {
        Mail mail = EmailBuilder.createMail(
                request.getEmail(),
                "GamesHub - account confirmation",
                "Welcome to GamesHub! This is confirmation message. Please click button below."
                );

        passwordEqualityValidator.validate(request.getPassword(), request.getRepeatPassword(), REGISTER_OPERATION);
        emailValidator.validateEmailDatabaseAbsence(request.getEmail(), REGISTER_OPERATION);
        emailValidator.validateEmailSmtpStatus(request.getEmail(), REGISTER_OPERATION);

        userFacade.signUpUser(AppUserMapper.mapToAppUser(request));

        emailUserService.sendNewAccConfirmationMail(mail, request.getFirstname(), request.getLoginName());

        return EMAIL_WITH_LINK_JUST_SEND;
    }

    public String confirmAccount(final User user) throws AccessDeniedException, UserAlreadyVerifiedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        if (!login.equals(user.getLoginName()) && !login.equalsIgnoreCase("admin")) throw new AccessDeniedException();
        if (user.isVerified()) throw new UserAlreadyVerifiedException();

        user.setVerified(true);
        userService.saveUser(user);

        return ACCOUNT_VERIFIED;
    }
}
