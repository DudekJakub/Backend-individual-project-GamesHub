package com.gameshub.subscribe;

public interface Visitor {

    void visitGameOpinionAddedEvent(final GameOpinionAddedEvent gameOpinionAddedEvent);
    void visitGameRatingAddedEvent(final GameRatingAddedEvent gameRatingAddedEvent);
}
