package com.gameshub.subscribe.strategy;

import com.gameshub.subscribe.GameOpinionAddedEvent;
import com.gameshub.subscribe.GameRatingAddedEvent;

public interface NotificationStrategy {

    void sendGameOpinionNotification(GameOpinionAddedEvent gameOpinionAddedEvent);
    void sendGameRatingNotification(GameRatingAddedEvent gameRatingAddedEvent);
}
