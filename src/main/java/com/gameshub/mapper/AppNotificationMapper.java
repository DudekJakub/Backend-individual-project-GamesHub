package com.gameshub.mapper;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.notification.AppNewOpinionNotifDto;
import com.gameshub.domain.notification.AppNewRatingNotifDto;
import com.gameshub.domain.notification.AppNotification;
import com.gameshub.mapper.game.RawgGameNameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AppNotificationMapper {

    public AppNewOpinionNotifDto mapToNewOpinionNotif(final AppNotification notification, final GameOpinion gameOpinion) {
        return AppNewOpinionNotifDto.builder()
                .id(notification.getId())
                .referencedGameId(gameOpinion.getGame().getId())
                .referencedEventId(gameOpinion.getId())
                .referencedGameName(RawgGameNameMapper.mapSlugNameToGameName(gameOpinion.getGameName()))
                .title(notification.getTitle())
                .newOpinion(gameOpinion.getOpinion())
                .notificationDate(notification.getNotificationDate())
                .build();
    }

    public LinkedHashSet<AppNewOpinionNotifDto> mapToNewOpinionNotifDtoLists(final Set<AppNotification> notifications, final List<GameOpinion> opinions) {
        LinkedHashSet<AppNewOpinionNotifDto> opinionNotifDtos = new LinkedHashSet<>();
        List<AppNotification> notifs = new ArrayList<>(notifications);

        for (int i = 0; i < notifications.size(); i++) {
            opinionNotifDtos.add(mapToNewOpinionNotif(notifs.get(i), opinions.get(i)));
        }
        return opinionNotifDtos;
    }

    public AppNewRatingNotifDto mapToNewRatingNotif(final AppNotification notification, final GameRating gameRating) {
        return AppNewRatingNotifDto.builder()
                .id(notification.getId())
                .referencedGameId(gameRating.getGame().getId())
                .referencedEventId(gameRating.getId())
                .referencedGameName(RawgGameNameMapper.mapSlugNameToGameName(gameRating.getGameName()))
                .title(notification.getTitle())
                .newRating(gameRating.getRating())
                .notificationDate(notification.getNotificationDate())
                .build();
    }

    public LinkedHashSet<AppNewRatingNotifDto> mapToNewRatingNotifDtoLists(final Set<AppNotification> notifications, final List<GameRating> ratings) {
        LinkedHashSet<AppNewRatingNotifDto> ratingNotifDtos = new LinkedHashSet<>();
        List<AppNotification> notifs = new ArrayList<>(notifications);

        if (notifications.size() > 0 && ratings.size() > 0)
        for (int i = 0; i < notifications.size(); i++) {
            ratingNotifDtos.add(mapToNewRatingNotif(notifs.get(i), ratings.get(i)));
        }
        return ratingNotifDtos;
    }
}
