package com.gameshub.subscribe;

import com.gameshub.domain.user.User;
import lombok.Data;

@Data
public abstract class SubscribeEvent {

    private final User user;
    private final String userName;
    private final String userEmail;

    public abstract void accept(Visitor visitor);
}
