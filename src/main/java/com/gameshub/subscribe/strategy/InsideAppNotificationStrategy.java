package com.gameshub.subscribe.strategy;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.user.User;
import com.gameshub.service.AppNotificationService;
import com.gameshub.subscribe.GameOpinionAddedEvent;
import com.gameshub.subscribe.GameRatingAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsideAppNotificationStrategy implements NotificationStrategy {

    private final AppNotificationService appNotificationService;

    @Override
    public void sendGameOpinionNotification(final GameOpinionAddedEvent gameOpinionAddedEvent) {
        User subscriber = gameOpinionAddedEvent.getUser();
        GameOpinion newOpinion = gameOpinionAddedEvent.getGameOpinion();
        appNotificationService.createNewOpinionNotif(subscriber, newOpinion);
    }

    @Override
    public void sendGameRatingNotification(final GameRatingAddedEvent gameRatingAddedEvent) {
        User subscriber = gameRatingAddedEvent.getUser();
        GameRating newRating = gameRatingAddedEvent.getGameRating();
        appNotificationService.createNewRatingNotif(subscriber, newRating);
    }
}
