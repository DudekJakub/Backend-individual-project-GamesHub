package com.gameshub.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppNewRatingNotifDto {

    private Long id;
    private Long referencedGameId;
    private Long referencedEventId;
    private double newRating;
    private String referencedGameName;
    private String title;
    private LocalDateTime notificationDate;
}
