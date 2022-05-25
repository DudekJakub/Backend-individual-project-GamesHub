package com.gameshub.mapper;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.notification.AppNewOpinionNotifDto;
import com.gameshub.domain.notification.AppNewRatingNotifDto;
import com.gameshub.domain.notification.AppNotification;
import com.gameshub.domain.notification.NotifStatus;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AppNotificationMapperTest {

    private final AppNotificationMapper mapper = new AppNotificationMapper();

    private AppNotification notification;
    private GameOpinion gameOpinion;
    private GameRating gameRating;
    private Game game;
    private User user;

    @BeforeEach
    void setSettings() {
        game = Game.builder()
                .id(4L)
                .name("name")
                .opinionsPerDay(3)
                .opinionsQnt(10)
                .averageRating(9)
                .ratingsQnt(4)
                .popularityStatus("LOW")
                .build();

        gameRating = GameRating.builder()
                .id(1L)
                .gameName("destiny-2")
                .rating(5)
                .game(game)
                .user(user)
                .build();

       notification = AppNotification.builder()
                .id(1L)
                .referencedEventId(1L)
                .referencedGameId(4L)
                .title("title")
                .systemStatus(NotifStatus.NORMAL)
                .build();

       gameOpinion = GameOpinion.builder()
                .id(1L)
                .gameName("destiny-2")
                .userLogin("admin")
                .opinion("test_opinion")
                .game(game)
                .user(user)
                .build();

       user = User.builder()
                .id(3L)
                .firstname("name")
                .lastname("lastName")
                .loginName("loginName")
                .email("email")
                .password("pass")
                .verified(true)
                .active(true)
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.SIMPLE_EMAIL_NOTIFICATION)
                .build();
    }

    @Test
    void mapToNewOpinionNotif() {
        //Given
            //setSettings()

        //When
        AppNewOpinionNotifDto notifDto = mapper.mapToNewOpinionNotif(notification, gameOpinion);

        //Then
        assertEquals(notification.getId(), notifDto.getId());
        assertEquals(notification.getTitle(), notifDto.getTitle());
        assertEquals(notification.getReferencedEventId(), notifDto.getReferencedEventId());
        assertEquals(notification.getReferencedGameId(), notifDto.getReferencedGameId());
    }

    @Test
    void mapToNewOpinionNotifDtoLists() {
        //Given
        Set<AppNotification> appNotifications = Set.of(notification);
        List<GameOpinion> opinions = List.of(gameOpinion);

        var notifToArray = new ArrayList<>(appNotifications);

        //When
        Set<AppNewOpinionNotifDto> notifDtos = mapper.mapToNewOpinionNotifDtoLists(appNotifications, opinions);

        var notifDtosToArray = new ArrayList<>(notifDtos);

        //Then
        assertEquals(1, notifDtos.size());
        assertEquals(notifToArray.get(0).getId(), notifDtosToArray.get(0).getId());
        assertEquals(notifToArray.get(0).getReferencedGameId(), notifDtosToArray.get(0).getReferencedGameId());
        assertEquals(notifToArray.get(0).getReferencedEventId(), notifDtosToArray.get(0).getReferencedEventId());
        assertEquals(notifToArray.get(0).getTitle(), notifDtosToArray.get(0).getTitle());
    }

    @Test
    void mapToNewRatingNotif() {
        //Given
            //setSettings()

        //When
        AppNewRatingNotifDto notifDto = mapper.mapToNewRatingNotif(notification, gameRating);

        //Then
        assertEquals(notification.getId(), notifDto.getId());
        assertEquals(notification.getTitle(), notifDto.getTitle());
        assertEquals(notification.getReferencedEventId(), notifDto.getReferencedEventId());
        assertEquals(notification.getReferencedGameId(), notifDto.getReferencedGameId());
    }

    @Test
    void mapToNewRatingNotifDtoLists() {
        //Given
        Set<AppNotification> appNotifications = Set.of(notification);
        List<GameRating> ratings = List.of(gameRating);

        var notifToArray = new ArrayList<>(appNotifications);

        //When
        Set<AppNewRatingNotifDto> notifDtos = mapper.mapToNewRatingNotifDtoLists(appNotifications, ratings);

        var notifDtosToArray = new ArrayList<>(notifDtos);

        //Then
        assertEquals(1, notifDtos.size());
        assertEquals(notifToArray.get(0).getId(),  notifDtosToArray.get(0).getId());
        assertEquals(notifToArray.get(0).getReferencedGameId(),  notifDtosToArray.get(0).getReferencedGameId());
        assertEquals(notifToArray.get(0).getReferencedEventId(),  notifDtosToArray.get(0).getReferencedEventId());
        assertEquals(notifToArray.get(0).getTitle(),  notifDtosToArray.get(0).getTitle());
    }
}