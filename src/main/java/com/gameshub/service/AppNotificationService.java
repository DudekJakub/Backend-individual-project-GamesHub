package com.gameshub.service;


import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.notification.AppNewOpinionNotifDto;
import com.gameshub.domain.notification.AppNewRatingNotifDto;
import com.gameshub.domain.notification.AppNotification;
import com.gameshub.domain.notification.NotifStatus;
import com.gameshub.domain.user.User;
import com.gameshub.exception.AppNotifNotFoundException;
import com.gameshub.exception.GameOpinionNotFoundException;
import com.gameshub.exception.GameRatingNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.mapper.AppNotificationMapper;
import com.gameshub.repository.AppNotificationRepository;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.repository.GameRatingRepository;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppNotificationService.class);
    private static final String NEW_OPINION_TITLE = "NEW OPINION";
    private static final String NEW_RATING_TITLE = "NEW RATING";

    private final AppNotificationRepository notifRepository;
    private final GameOpinionRepository opinionRepository;
    private final GameRatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final AppNotificationMapper notifMapper;

    public void createNewOpinionNotif(final User user, final GameOpinion newOpinion) {
        AppNotification newNotif = AppNotification.builder()
                                                  .title(NEW_OPINION_TITLE)
                                                  .referencedEventId(newOpinion.getId())
                                                  .referencedGameId(newOpinion.getGame().getId())
                                                  .systemStatus(NotifStatus.NORMAL)
                                                  .build();
        newNotif.getNotificatedUsers().add(user);
        notifRepository.save(newNotif);
    }

    public void createNewRatingNotif(final User user, final GameRating gameRating) {
        AppNotification newNotif = AppNotification.builder()
                                                  .title(NEW_RATING_TITLE)
                                                  .systemStatus(NotifStatus.NORMAL)
                                                  .referencedEventId(gameRating.getId())
                                                  .referencedGameId(gameRating.getGame().getId())
                                                  .systemStatus(NotifStatus.NORMAL)
                                                  .build();
        newNotif.getNotificatedUsers().add(user);
        notifRepository.save(newNotif);
    }

    public Set<AppNewOpinionNotifDto> getUserNewOpinionNotifs(final Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<GameOpinion> opinions = new ArrayList<>();
        Set<AppNotification> opinionNotifs = user.getAppNotifications();
        List<Long> opinionIds = pullEventEntityIdsFromUserNotifs(opinionNotifs, NEW_OPINION_TITLE);

        for (Long opinionId : opinionIds) {
            try {
                opinions.add(opinionRepository.findById(opinionId).orElseThrow(GameOpinionNotFoundException::new));
            } catch (GameOpinionNotFoundException e) {
                LOGGER.warn("Opinion for NEW OPINION NOTIFS NOT FOUND! " + e.getMessage());
            }
        }
        return notifMapper.mapToNewOpinionNotifDtoLists(opinionNotifs, opinions);
    }

    public Set<AppNewRatingNotifDto> getUserNewRatingNotifs(final Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<GameRating> ratings = new ArrayList<>();
        Set<AppNotification> ratingNotifs = user.getAppNotifications();
        List<Long> ratingIds = pullEventEntityIdsFromUserNotifs(ratingNotifs, NEW_RATING_TITLE);

        for (Long ratingId : ratingIds) {
            try {
                ratings.add(ratingRepository.findById(ratingId).orElseThrow(GameRatingNotFoundException::new));
            } catch (GameRatingNotFoundException e) {
                LOGGER.warn("Rating for NEW RATING NOTIFS NOT FOUND! " + e.getMessage());
            }
        }
        return notifMapper.mapToNewRatingNotifDtoLists(ratingNotifs, ratings);
    }

    public boolean deleteNotif(final Long notifId, final Long userId) throws AppNotifNotFoundException, UserNotFoundException {
        AppNotification notifToDelete = notifRepository.findById(notifId).orElseThrow(AppNotifNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        notifToDelete.getNotificatedUsers().remove(user);
        notifRepository.save(notifToDelete);

        return true;
    }

    private List<Long> pullEventEntityIdsFromUserNotifs(final Set<AppNotification> userNotifs, final String entityType) {
        return userNotifs.stream()
                .filter(notif -> notif.getTitle().equals(entityType))
                .map(AppNotification::getReferencedEventId)
                .collect(Collectors.toList());
    }
}
