package com.gameshub.email;

import com.gameshub.domain.mail.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailBuilder emailBuilder;
    private final JavaMailSender javaMailSender;

    public void sendNewAccConfirmationMail(final Mail mail, final String userName, final String loginName) {
        log.info("Starting email preparation...");
        try {
            javaMailSender.send(createMailMessage(mail, userName, loginName));
        } catch (MailException mE) {
            log.error("Failed to process email sending: " + mE.getMessage());
        }
        log.info("Email has been send!");
    }

    private MimeMessagePreparator createMailMessage(final Mail mail, final String userName, final String loginName) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(emailBuilder.buildAccountCreatedConfirmationEmail(mail.getMessage(), userName, loginName), true);
        };
    }
}
