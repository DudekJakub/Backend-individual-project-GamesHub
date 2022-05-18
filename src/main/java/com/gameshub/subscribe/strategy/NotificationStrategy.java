package com.gameshub.subscribe.strategy;

import com.gameshub.subscribe.GameOpinionAddedEvent;

public interface NotificationStrategy {

    void sendGameOpinionNotification(GameOpinionAddedEvent gameOpinionAddedEvent);
    void sendGameRatingNotification();
}
