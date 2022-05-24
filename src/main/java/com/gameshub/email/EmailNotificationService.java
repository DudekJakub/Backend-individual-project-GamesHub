package com.gameshub.email;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.mail.Mail;
import com.gameshub.email.builder.EmailNotificationBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationService.class);

    private final EmailNotificationBuilder emailNotificationBuilder;
    private final JavaMailSender javaMailSender;

    public void sendGameOpinionNotifyingMail(final Mail mail, final String userName, final GameOpinion gameOpinion,
                                             final List<List<String>> gameAndOpinionInformation) {
        try {
            LOGGER.info("Starting game opinion notifying email preparation...");
            javaMailSender.send(createMailMessageForGameOpinionsNotifying(mail, userName, gameOpinion, gameAndOpinionInformation));
            LOGGER.info("Email has been send!");
        } catch (MailException mE) {
            Long targetUserId = gameOpinion.getUser().getId();
            LOGGER.warn("Failed to process email sending for user with ID: " + targetUserId + " ! " + mE.getMessage());
        }
    }

    public void sendGameRatingNotifyingMail(final Mail mail, final String userName, final GameRating gameRating,
                                           final List<List<String>> gameAndRatingInformation) {
        try {
            LOGGER.info("Starting game rating notifying email preparation...");
            javaMailSender.send(createMailMessageForGameRatingsNotifying(mail, userName, gameRating, gameAndRatingInformation));
            LOGGER.info("Email has been send!");
        } catch (MailException mE) {
            Long targetUserId = gameRating.getUser().getId();
            LOGGER.warn("Failed to process email sending for user with ID: " + targetUserId + " ! " + mE.getMessage());
        }
    }

    private MimeMessagePreparator createMailMessageForGameOpinionsNotifying(final Mail mail, final String userName, final GameOpinion gameOpinion,
                                                                            final List<List<String>> gameAndOpinionInformation) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(emailNotificationBuilder.buildGameOpinionNotifyingEmail(mail.getMessage(), userName, gameOpinion, gameAndOpinionInformation), true);
        };
    }

    private MimeMessagePreparator createMailMessageForGameRatingsNotifying(final Mail mail, final String userName, final GameRating gameRating,
                                                                           final List<List<String>> gameAndRatingInformation) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(emailNotificationBuilder.buildGameRatingNotifyingEmail(mail.getMessage(), userName, gameRating, gameAndRatingInformation), true);
        };
    }
}
