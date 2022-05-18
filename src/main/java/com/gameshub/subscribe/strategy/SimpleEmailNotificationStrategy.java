package com.gameshub.subscribe.strategy;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.mail.Mail;
import com.gameshub.email.EmailBuilder;
import com.gameshub.email.EmailService;
import com.gameshub.subscribe.GameOpinionAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleEmailNotificationStrategy implements NotificationStrategy {

    private final EmailService emailService;

    @Override
    public void sendGameOpinionNotification(GameOpinionAddedEvent gameOpinionAddedEvent) {
        String userName = gameOpinionAddedEvent.getUserName();
        String newOpinion = gameOpinionAddedEvent.getNewOpinion();
        String userEmail = gameOpinionAddedEvent.getUserEmail();
        GameOpinion gameOpinion = gameOpinionAddedEvent.getGameOpinion();
        int opinionsQnt = gameOpinionAddedEvent.getOpinionsQnt();

        Mail mail = EmailBuilder.createMail(
                userEmail,
                "GamesHub - new opinion notification",
                "There is new opinion for game You have subscribed!"
        );
        emailService.sendGameOpinionNotifyingMail(mail, userName, gameOpinion, opinionsQnt, newOpinion);
    }

    @Override
    public void sendGameRatingNotification() {

    }
}
