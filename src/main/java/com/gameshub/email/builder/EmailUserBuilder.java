package com.gameshub.email.builder;

import com.gameshub.configuration.AdminConfiguration;
import com.gameshub.domain.user.User;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailUserBuilder extends EmailBuilder {

    private static final String MAIN_PAGE_AFTER_ACC_CONFIRMATION = "http://localhost:8080/v1/registrations/confirm/";
    private static final String USER_ACCOUNT_PAGE = "http://localhost:8080/v1/users/restricted/";
    private static final String HTTP_CONFIRMATION_MAIL = "mail/created-account-confirmation-email";
    private static final String HTTP_SUMMARY_MAIL = "mail/user-summary-email";

    public EmailUserBuilder(ITemplateEngine iTemplateEngine, AdminConfiguration adminConfiguration) {
        super(iTemplateEngine, adminConfiguration);
    }

    public String buildAccountCreatedConfirmationEmail(final String message, final String userName, final String loginName) {
        String previewMessage = "ACCOUNT CREATED CONFIRMATION EMAIL";
        String buttonName = "CONFIRM YOUR ACCOUNT";

        List<String> terms = prepareApplicationTermsList();

        Context context = new Context();
        setContext(context, message, userName, MAIN_PAGE_AFTER_ACC_CONFIRMATION + loginName);
        context.setVariable("preview_message", previewMessage);
        context.setVariable("button", buttonName);
        context.setVariable("application_terms", terms);

        return iTemplateEngine.process(HTTP_CONFIRMATION_MAIL, context);
    }

    public String buildUserSummaryEmail(final String message, final User user) {
        String previewMessage = "USER'S SUMMARY INFORMATION EMAIL";
        String buttonName = "YOUR ACCOUNT PAGE";

        List<String> userInformation = prepareUserInformation(user);

        Context context = new Context();
        setContext(context, message, user.getFirstname(), USER_ACCOUNT_PAGE + user.getId());
        context.setVariable("preview_message", previewMessage);
        context.setVariable("button", buttonName);
        context.setVariable("user_information", userInformation);

        return iTemplateEngine.process(HTTP_SUMMARY_MAIL, context);
    }

    private List<String> prepareApplicationTermsList() { //TO DO !
        return new ArrayList<>();
    }

    private List<String> prepareUserInformation(final User user) {
        long userDaysWithApp = ChronoUnit.DAYS.between(user.getRegisteredDate().toLocalDate(), LocalDate.now());

        List<String> userInformation = new ArrayList<>();
        userInformation.add("DAYS WITH APPLICATION: " + userDaysWithApp);
        userInformation.add("GAMES OWNED: " + user.getGamesOwned().size());
        userInformation.add("GAMES WANTED: " + user.getGamesWantedToOwn().size());
        userInformation.add("OPINIONS: " + user.getOpinionsQnt());
        userInformation.add("RATINGS: " + user.getRatingsQnt());
        userInformation.add("AVG. OPINIONS PER DAY: " + user.getOpinionsPerDay());
        userInformation.add("AVG. RATINGS PER DAT: " + user.getRatingsPerDay());

        return userInformation;
    }
}
