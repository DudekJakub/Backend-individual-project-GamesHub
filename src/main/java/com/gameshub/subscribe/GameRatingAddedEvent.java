package com.gameshub.subscribe;

import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.user.User;
import com.gameshub.mapper.game.RawgGameNameMapper;
import lombok.Getter;

@Getter
public final class GameRatingAddedEvent extends SubscribeEvent {

    private final double newRating;
    private final GameRating gameRating;

    public GameRatingAddedEvent(final User user, final double newRating, final GameRating gameRating) {
        super(user);
        this.newRating = newRating;
        this.gameRating = gameRating;
        this.gameName = RawgGameNameMapper.mapSlugNameToGameName(gameRating.getGameName());
        this.gameId = gameRating.getGame().getId();
        this.opinionsQnt = gameRating.getGame().getOpinionsQnt();
        this.ratingsQnt = gameRating.getGame().getRatingsQnt();
        this.opinionsPerDay = gameRating.getGame().getOpinionsPerDay();
        this.ratingsPerDay = gameRating.getGame().getRatingsPerDay();
        this.averageCurrentRating = gameRating.getGame().getAverageRating();
        this.pubDate = gameRating.getPublicationDate();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitGameRatingAddedEvent(this);
    }
}
