package com.gameshub.subscribe;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.user.User;
import lombok.Getter;

@Getter
public class GameOpinionAddedEvent extends SubscribeEvent {

    private final String newOpinion;
    private final GameOpinion gameOpinion;
    private final int opinionsQnt;

    public GameOpinionAddedEvent(User user, String userName, String userEmail, String newOpinion,
                                 GameOpinion gameOpinion, int opinionsQnt) {
        super(user, userName, userEmail);
        this.newOpinion = newOpinion;
        this.gameOpinion = gameOpinion;
        this.opinionsQnt = opinionsQnt;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitGameOpinionAddedEvent(this);
    }
}
