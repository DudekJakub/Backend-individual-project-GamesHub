package com.gameshub.subscribe.strategy;

import com.gameshub.email.EmailService;
import com.gameshub.subscribe.GameOpinionAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FullMailNotificationStrategy implements NotificationStrategy {

    private final EmailService emailService;

    @Override
    public void sendGameOpinionNotification(GameOpinionAddedEvent gameOpinionAddedEvent) {

    }

    @Override
    public void sendGameRatingNotification() {

    }
}
