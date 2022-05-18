package com.gameshub.subscribe;

import com.gameshub.domain.user.User;
import com.gameshub.subscribe.strategy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserNotifier {

    private final FullMailNotificationStrategy fullMailNotificationStrategy;
    private final SimpleEmailNotificationStrategy simpleEmailNotificationStrategy;
    private final InsideAppNotificationStrategy insideAppNotificationStrategy;

    public NotificationStrategy getUserNotificationStrategy(final User userToNotify) {

        switch (userToNotify.getNotificationStrategy()) {
            case FULL_EMAIL_NOTIFICATION:
                return fullMailNotificationStrategy;
            case INSIDE_APP_NOTIFICATION:
                return insideAppNotificationStrategy;
            default:
                return simpleEmailNotificationStrategy;
        }
    }
}
