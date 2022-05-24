package com.gameshub.subscribe;

import com.gameshub.domain.user.User;
import com.gameshub.subscribe.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeEventVisitor implements Visitor {

    private final UserNotifier userNotifier;

    @Override
    public void visitGameOpinionAddedEvent(final GameOpinionAddedEvent gameOpinionAddedEvent) {
        User userToNotify = gameOpinionAddedEvent.getUser();

        NotificationStrategy notificationStrategy = userNotifier.getUserNotificationStrategy(userToNotify);
        notificationStrategy.sendGameOpinionNotification(gameOpinionAddedEvent);
    }

    @Override
    public void visitGameRatingAddedEvent(final GameRatingAddedEvent gameRatingAddedEvent) {
        User userToNotify = gameRatingAddedEvent.getUser();

        NotificationStrategy notificationStrategy = userNotifier.getUserNotificationStrategy(userToNotify);
        notificationStrategy.sendGameRatingNotification(gameRatingAddedEvent);
    }
}
