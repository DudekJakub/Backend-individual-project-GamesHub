package com.gameshub.domain.game;

import com.gameshub.domain.user.User;

public interface GameDataSource {

    String getGameName();
    Game getGame();
    User getUser();
}
