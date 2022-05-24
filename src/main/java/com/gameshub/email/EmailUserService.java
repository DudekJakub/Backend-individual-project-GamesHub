package com.gameshub.email;

import com.gameshub.domain.mail.Mail;
import com.gameshub.domain.user.User;
import com.gameshub.email.builder.EmailUserBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUserService.class);

    private final EmailUserBuilder emailUserBuilder;
    private final JavaMailSender javaMailSender;

    public void sendNewAccConfirmationMail(final Mail mail, final String userName, final String loginName) {
        try {
            LOGGER.info("Starting confirmation email preparation...");
            javaMailSender.send(createAccConfirmationMailMessage(mail, userName, loginName));
            LOGGER.info("Email has been send!");
        } catch (MailException mE) {
            LOGGER.warn("Failed to process email sending: " + mE.getMessage());
        }
    }

    public void sendUserSummaryMail(final Mail mail, final User user) {
        try {
            LOGGER.info("Starting user summary email preparation...");
            javaMailSender.send(createUserSummaryMailMessage(mail, user));
            LOGGER.info("Email has been send!");
        } catch (MailException mE) {
            LOGGER.warn("Failed to process email sending: " + mE.getMessage());
        }
    }

    private MimeMessagePreparator createAccConfirmationMailMessage(final Mail mail, final String userName, final String loginName) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(emailUserBuilder.buildAccountCreatedConfirmationEmail(mail.getMessage(), userName, loginName), true);
        };
    }

    private MimeMessagePreparator createUserSummaryMailMessage(final Mail mail, final User user) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(emailUserBuilder.buildUserSummaryEmail(mail.getMessage(), user), true);
        };
    }
}
