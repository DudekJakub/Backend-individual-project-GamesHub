package com.gameshub.subscribe;

import com.gameshub.domain.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class SubscribeEvent {

    private final User user;
    private final String userName;
    private final String userEmail;
    protected String gameName;
    protected Long gameId;
    protected int opinionsQnt;
    protected int ratingsQnt;
    protected double opinionsPerDay;
    protected double ratingsPerDay;
    protected double averageCurrentRating;
    protected LocalDateTime pubDate;

    public SubscribeEvent(User user) {
        this.user = user;
        this.userName = user.getFirstname();
        this.userEmail = user.getEmail();
    }

    public abstract void accept(Visitor visitor);
}
