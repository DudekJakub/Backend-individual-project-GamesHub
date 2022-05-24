package com.gameshub.email;

import com.gameshub.domain.mail.Mail;
import com.gameshub.domain.user.User;
import com.gameshub.email.builder.EmailAdminBuilder;
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
public class EmailAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailAdminService.class);

    private final EmailAdminBuilder emailAdminBuilder;
    private final JavaMailSender javaMailSender;

    public void sendAppStatisticMail(final Mail mail, final User user) {
        try {
            LOGGER.info("Starting app statistic email preparation...");
            javaMailSender.send(createAppStatisticMailMessage(mail, user));
            LOGGER.info("Email has been send!");
        } catch (MailException mE) {
            LOGGER.warn("Failed to process email sending: " + mE.getMessage());
        }
    }

    private MimeMessagePreparator createAppStatisticMailMessage(final Mail mail, final User user) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(emailAdminBuilder.buildAdminStatisticsEmail(mail.getMessage(), user), true);
        };
    }
}
