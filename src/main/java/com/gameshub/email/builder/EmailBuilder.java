package com.gameshub.email.builder;

import com.gameshub.configuration.AdminConfiguration;
import com.gameshub.domain.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Component
public abstract class EmailBuilder {

    private static final String GOODBYE_MESSAGE = "Hope You enjoy my application!";

    @Qualifier("templateEngine")
    protected final ITemplateEngine iTemplateEngine;

    protected final AdminConfiguration adminConfiguration;

    @Autowired
    public EmailBuilder(ITemplateEngine iTemplateEngine, AdminConfiguration adminConfiguration) {
        this.iTemplateEngine = iTemplateEngine;
        this.adminConfiguration = adminConfiguration;
    }

    public static Mail createMail(final String mailTo, final String subject, final String message) {
        return Mail.builder()
                .mailTo(mailTo)
                .subject(subject)
                .message(message)
                .build();
    }

    protected void setContext(final Context context, final String message, final String userName, final String defaultPageURL) {
        context.setVariable("user", userName);
        context.setVariable("message", message);
        context.setVariable("show_button", true);
        context.setVariable("default_url", defaultPageURL);
        context.setVariable("is_user", true);
        context.setVariable("goodbye_message", GOODBYE_MESSAGE);
        context.setVariable("company_detail_name", adminConfiguration.getCompanyName());
        context.setVariable("company_detail_email", adminConfiguration.getCompanyEmail());
        context.setVariable("company_detail_phone", adminConfiguration.getCompanyPhone());
    }
}
