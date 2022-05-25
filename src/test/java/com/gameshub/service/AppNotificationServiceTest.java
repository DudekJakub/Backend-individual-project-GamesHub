package com.gameshub.service;

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
import com.gameshub.exception.AppNotifNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.mapper.AppNotificationMapper;
import com.gameshub.repository.AppNotificationRepository;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.repository.GameRatingRepository;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppNotificationServiceTest {

    @InjectMocks
    private AppNotificationService service;

    @Mock
    private AppNotificationRepository notifRepository;
    @Mock
    private GameOpinionRepository opinionRepository;
    @Mock
    private GameRatingRepository ratingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AppNotificationMapper notifMapper;

    private User userForTest;
    private Game gameForTest;
    private GameOpinion opinionForTest;
    private GameRating ratingForTest;

    @BeforeEach
    void setSettings() {
        userForTest = User.builder()
                .id(1L)
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.FULL_EMAIL_NOTIFICATION)
                .email("test_email")
                .password("test_password")
                .firstname("test_firstname")
                .lastname("test_lastname")
                .active(false)
                .verified(true)
                .build();

        gameForTest = Game.builder()
                .id(1L)
                .name("test_game")
                .build();

        opinionForTest = GameOpinion.builder()
                .id(1L)
                .opinion("test_opinion")
                .game(gameForTest)
                .user(userForTest)
                .gameName(gameForTest.getName())
                .userLogin(userForTest.getLoginName())
                .build();

        ratingForTest = GameRating.builder()
                .id(1L)
                .rating(5.0)
                .game(gameForTest)
                .user(userForTest)
                .gameName(gameForTest.getName())
                .user(userForTest)
                .build();
    }

    @Test
    void createNewOpinionNotif() {
        //Given
        AppNotification newNotif = AppNotification.builder()
                .title("NEW OPINION")
                .referencedEventId(opinionForTest.getId())
                .referencedGameId(opinionForTest.getGame().getId())
                .systemStatus(NotifStatus.NORMAL)
                .build();
        newNotif.getNotificatedUsers().add(userForTest);

        lenient().when(notifRepository.save(newNotif)).thenReturn(newNotif);

        //When
        service.createNewOpinionNotif(userForTest, opinionForTest);

        //Then
        verify(notifRepository, times(1)).save(any());
    }

    @Test
    void createNewRatingNotif() {
        //Given
        AppNotification newNotif = AppNotification.builder()
                .title("NEW RATING")
                .referencedEventId(ratingForTest.getId())
                .referencedGameId(ratingForTest.getGame().getId())
                .systemStatus(NotifStatus.NORMAL)
                .build();
        newNotif.getNotificatedUsers().add(userForTest);

        lenient().when(notifRepository.save(newNotif)).thenReturn(newNotif);

        //When
        service.createNewRatingNotif(userForTest, ratingForTest);

        //Then
        verify(notifRepository, times(1)).save(any());
    }

    @Test
    void getUserNewOpinionNotifs() throws UserNotFoundException {
        //Given
        Long userId = 1L;

        GameOpinion opinionForTest2 = GameOpinion.builder()
                .id(2L)
                .opinion("test_opinion2")
                .game(gameForTest)
                .user(userForTest)
                .gameName(gameForTest.getName())
                .userLogin(userForTest.getLoginName())
                .build();

        AppNotification newNotif1 = AppNotification.builder()
                .id(1L)
                .title("NEW OPINION")
                .referencedEventId(opinionForTest.getId())
                .referencedGameId(opinionForTest.getGame().getId())
                .systemStatus(NotifStatus.NORMAL)
                .build();

        AppNotification newNotif2 = AppNotification.builder()
                .id(2L)
                .title("NEW OPINION")
                .referencedEventId(opinionForTest2.getId())
                .referencedGameId(opinionForTest2.getGame().getId())
                .systemStatus(NotifStatus.NORMAL)
                .build();

        AppNewOpinionNotifDto newOpinionNotif1Dto = AppNewOpinionNotifDto.builder()
                        .id(1L)
                        .newOpinion(opinionForTest.getOpinion())
                        .title("NEW OPINION")
                        .referencedEventId(opinionForTest.getId())
                        .referencedGameId(opinionForTest.getGame().getId())
                        .build();

        AppNewOpinionNotifDto newOpinionNotif2Dto = AppNewOpinionNotifDto.builder()
                .id(2L)
                .newOpinion(opinionForTest2.getOpinion())
                .title("NEW OPINION")
                .referencedEventId(opinionForTest2.getId())
                .referencedGameId(opinionForTest2.getGame().getId())
                .build();

        userForTest.getAppNotifications().add(newNotif1);
        userForTest.getAppNotifications().add(newNotif2);

        LinkedHashSet<AppNewOpinionNotifDto> opinionNotifDtos = new LinkedHashSet<>();
        opinionNotifDtos.add(newOpinionNotif1Dto);
        opinionNotifDtos.add(newOpinionNotif2Dto);

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(userForTest));
        when(opinionRepository.findById(1L)).thenReturn(Optional.ofNullable(opinionForTest));
        when(opinionRepository.findById(2L)).thenReturn(Optional.of(opinionForTest2));
        when(notifMapper.mapToNewOpinionNotifDtoLists(any(), any())).thenReturn(opinionNotifDtos);

        //When
        List<AppNewOpinionNotifDto> resultList = new ArrayList<>(service.getUserNewOpinionNotifs(userId));

        //Then
        assertEquals(2, resultList.size());
        assertEquals(newNotif1.getTitle(), resultList.get(0).getTitle());
        assertEquals(newNotif1.getId(), resultList.get(0).getId());
        assertEquals(newNotif1.getReferencedEventId(), resultList.get(0).getReferencedEventId());
        assertEquals(newNotif1.getReferencedGameId(), resultList.get(0).getReferencedGameId());
        assertEquals(newNotif2.getTitle(), resultList.get(1).getTitle());
        assertEquals(newNotif2.getId(), resultList.get(1).getId());
        assertEquals(newNotif2.getReferencedEventId(), resultList.get(1).getReferencedEventId());
        assertEquals(newNotif2.getReferencedGameId(), resultList.get(1).getReferencedGameId());
    }

    @Test
    void getUserNewRatingNotifs() throws UserNotFoundException {
        //Given
        Long userId = 1L;

        GameRating ratingForTest2 = GameRating.builder()
                .id(2L)
                .rating(6.0)
                .game(gameForTest)
                .user(userForTest)
                .gameName(gameForTest.getName())
                .build();

        AppNotification newNotif1 = AppNotification.builder()
                .id(1L)
                .title("NEW RATING")
                .referencedEventId(ratingForTest.getId())
                .referencedGameId(ratingForTest.getGame().getId())
                .systemStatus(NotifStatus.NORMAL)
                .build();

        AppNotification newNotif2 = AppNotification.builder()
                .id(2L)
                .title("NEW RATING")
                .referencedEventId(ratingForTest2.getId())
                .referencedGameId(ratingForTest2.getGame().getId())
                .systemStatus(NotifStatus.NORMAL)
                .build();

        AppNewRatingNotifDto newRatingNotif1Dto = AppNewRatingNotifDto.builder()
                .id(1L)
                .newRating(ratingForTest.getRating())
                .title("NEW RATING")
                .referencedEventId(opinionForTest.getId())
                .referencedGameId(opinionForTest.getGame().getId())
                .build();

        AppNewRatingNotifDto newRatingNotif2Dto = AppNewRatingNotifDto.builder()
                .id(2L)
                .newRating(ratingForTest.getRating())
                .title("NEW RATING")
                .referencedEventId(ratingForTest2.getId())
                .referencedGameId(ratingForTest2.getGame().getId())
                .build();

        userForTest.getAppNotifications().add(newNotif1);
        userForTest.getAppNotifications().add(newNotif2);

        LinkedHashSet<AppNewRatingNotifDto> ratingNotifDtos = new LinkedHashSet<>();
        ratingNotifDtos.add(newRatingNotif1Dto);
        ratingNotifDtos.add(newRatingNotif2Dto);


        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(userForTest));
        when(ratingRepository.findById(1L)).thenReturn(Optional.ofNullable(ratingForTest));
        when(ratingRepository.findById(2L)).thenReturn(Optional.of(ratingForTest2));
        when(notifMapper.mapToNewRatingNotifDtoLists(any(), any())).thenReturn(ratingNotifDtos);

        //When
        List<AppNewRatingNotifDto> resultList = List.copyOf(service.getUserNewRatingNotifs(userId));

        //Then
        assertEquals(2, resultList.size());
        assertEquals(newNotif1.getTitle(), resultList.get(0).getTitle());
        assertEquals(newNotif1.getId(), resultList.get(0).getId());
        assertEquals(newNotif1.getReferencedEventId(), resultList.get(0).getReferencedEventId());
        assertEquals(newNotif1.getReferencedGameId(), resultList.get(0).getReferencedGameId());
        assertEquals(newNotif2.getTitle(), resultList.get(1).getTitle());
        assertEquals(newNotif2.getId(), resultList.get(1).getId());
        assertEquals(newNotif2.getReferencedEventId(), resultList.get(1).getReferencedEventId());
        assertEquals(newNotif2.getReferencedGameId(), resultList.get(1).getReferencedGameId());
        assertEquals(newRatingNotif2Dto.getNewRating(), resultList.get(1).getNewRating());
    }

    @Test
    void deleteNotif() throws UserNotFoundException, AppNotifNotFoundException {
        //Given
        Long notifId = 1L;
        Long userId = 1L;

        AppNotification newNotif1 = AppNotification.builder()
                .id(1L)
                .title("NEW RATING")
                .referencedEventId(ratingForTest.getId())
                .referencedGameId(ratingForTest.getGame().getId())
                .systemStatus(NotifStatus.NORMAL)
                .build();

        userForTest.getAppNotifications().add(newNotif1);
        newNotif1.getNotificatedUsers().add(userForTest);

        when(notifRepository.findById(notifId)).thenReturn(Optional.of(newNotif1));
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(userForTest));
        when(notifRepository.save(newNotif1)).thenReturn(newNotif1);

        //When
        boolean notifDeleted = service.deleteNotif(notifId, userId);

        //Then
        assertTrue(notifDeleted);
        assertFalse(userForTest.getAppNotifications().contains(newNotif1));
        assertFalse(newNotif1.getNotificatedUsers().contains(userForTest));
    }
}