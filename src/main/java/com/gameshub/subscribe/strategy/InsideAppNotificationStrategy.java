package com.gameshub.subscribe.strategy;

import com.gameshub.subscribe.GameOpinionAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsideAppNotificationStrategy implements NotificationStrategy {

    @Override
    public void sendGameOpinionNotification(GameOpinionAddedEvent gameOpinionAddedEvent) {

    }

    @Override
    public void sendGameRatingNotification() {

    }
}
