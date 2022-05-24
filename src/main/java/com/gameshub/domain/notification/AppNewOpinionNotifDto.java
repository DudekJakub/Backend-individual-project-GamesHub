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
public class AppNewOpinionNotifDto {

    private Long id;
    private Long referencedGameId;
    private Long referencedEventId;
    private String referencedGameName;
    private String title;
    private String newOpinion;
    private LocalDateTime notificationDate;
}
