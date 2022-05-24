package com.gameshub.subscribe;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.user.User;
import com.gameshub.mapper.game.RawgGameNameMapper;
import lombok.Getter;

@Getter
public final class GameOpinionAddedEvent extends SubscribeEvent {

    private final String newOpinion;
    private final GameOpinion gameOpinion;

    public GameOpinionAddedEvent(final User user, final String newOpinion, final GameOpinion gameOpinion) {
        super(user);
        this.newOpinion = newOpinion;
        this.gameOpinion = gameOpinion;
        this.gameName = RawgGameNameMapper.mapSlugNameToGameName(gameOpinion.getGameName());
        this.gameId = gameOpinion.getGame().getId();
        this.opinionsQnt = gameOpinion.getGame().getOpinionsQnt();
        this.ratingsQnt = gameOpinion.getGame().getRatingsQnt();
        this.opinionsPerDay = gameOpinion.getGame().getOpinionsPerDay();
        this.ratingsPerDay = gameOpinion.getGame().getRatingsPerDay();
        this.averageCurrentRating = gameOpinion.getGame().getAverageRating();
        this.pubDate = gameOpinion.getPublicationDate();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitGameOpinionAddedEvent(this);
    }
}
