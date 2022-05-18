package com.gameshub.email;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.mail.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final EmailBuilder emailBuilder;
    private final JavaMailSender javaMailSender;

    public void sendNewAccConfirmationMail(final Mail mail, final String userName, final String loginName) {
        try {
            LOGGER.info("Starting confirmation email preparation...");
            javaMailSender.send(createMailMessageForAccConfirmation(mail, userName, loginName));
            LOGGER.info("Email has been send!");
        } catch (MailException mE) {
            LOGGER.error("Failed to process email sending: " + mE.getMessage());
        }
    }

    public void sendGameOpinionNotifyingMail(final Mail mail, final String userName, final GameOpinion gameOpinion,
                                             final int opinionQnt, final String userOpinion) {
        try {
            LOGGER.info("Starting game opinion notifying email preparation...");
            javaMailSender.send(createMailMessageForGameOpinionsNotifying(mail, userName, gameOpinion, opinionQnt, userOpinion));
            LOGGER.info("Email has been send!");
        } catch (MailException mE) {
            Long targetUserId = gameOpinion.getUser().getId();
            LOGGER.error("Failed to process email sending for user with ID : " + targetUserId + " ! " + mE.getMessage() );
        }
    }

    private MimeMessagePreparator createMailMessageForAccConfirmation(final Mail mail, final String userName, final String loginName) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(emailBuilder.buildAccountCreatedConfirmationEmail(mail.getMessage(), userName, loginName), true);
        };
    }

    private MimeMessagePreparator createMailMessageForGameOpinionsNotifying(final Mail mail, final String userName, final GameOpinion gameOpinion,
                                                                            final int opinionQnt, final String userOpinion) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(emailBuilder.buildGameOpinionNotifyingEmail(mail.getMessage(), userName, gameOpinion, opinionQnt, userOpinion), true);
        };
    }
}
