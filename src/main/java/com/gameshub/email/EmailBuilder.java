package com.gameshub.email;

import com.gameshub.configuration.AdminConfiguration;
import com.gameshub.domain.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailBuilder {

    private static final String MAIN_PAGE_AFTER_ACC_CONFIRMATION = "http://localhost:8080/v1/registration/confirm/";
    private static final String HTTP_CONFIRMATION_MAIL = "mail/created-account-confirmation-email";
    private static final String PREVIEW_MESSAGE = "ACCOUNT CREATED CONFIRMATION EMAIL";
    private static final String BUTTON_VALUE = "CONFIRM YOUR ACCOUNT";
    private static final String GOODBYE_MESSAGE = "Hope You enjoy my application!";

    @Qualifier("templateEngine")
    @Autowired
    private ITemplateEngine iTemplateEngine;

    @Autowired
    private AdminConfiguration adminConfiguration;

    public String buildAccountCreatedConfirmationEmail(final String message, final String userName, final String loginName) {

        List<String> terms = new ArrayList<>();
        terms.add("These are my app rules");

        Context context = new Context();
        context.setVariable("preview_message", PREVIEW_MESSAGE);
        context.setVariable("user", userName);
        context.setVariable("is_user", true);
        context.setVariable("message", message);
        context.setVariable("button", BUTTON_VALUE);
        context.setVariable("show_button", true);
        context.setVariable("default_url", MAIN_PAGE_AFTER_ACC_CONFIRMATION + loginName);
        context.setVariable("application_terms", terms);
        context.setVariable("goodbye_message", GOODBYE_MESSAGE);
        context.setVariable("company_detail_name", adminConfiguration.getCompanyName());
        context.setVariable("company_detail_email", adminConfiguration.getCompanyEmail());
        context.setVariable("company_detail_phone", adminConfiguration.getCompanyPhone());

        return iTemplateEngine.process(HTTP_CONFIRMATION_MAIL, context);
    }

    public static Mail createMail(final String mailTo, final String subject, final String message) {
        return Mail.builder()
                .mailTo(mailTo)
                .subject(subject)
                .message(message)
                .build();
    }
}
