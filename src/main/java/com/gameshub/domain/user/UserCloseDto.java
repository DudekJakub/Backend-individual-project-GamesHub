package com.gameshub.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCloseDto {

    private Long id;
    private String loginName;
    private String firstname;
    private String lastname;
    private String email;
    private AppUserRole appUserRole;
    private AppUserNotificationStrategy notificationStrategy;
    private LocalDateTime registeredDate;
    private boolean active;
    private boolean verified;
    private int opinionsQnt;
    private double opinionsPerDay;
    private int ratingsQnt;
    private double ratingsPerDay;
    private List<Long> gameOpinionsIds;
    private List<Long> gameRatingsIds;
    private Set<Long> gamesOwnedIds;
    private Set<Long> gamesWantedIds;
    private Set<Long> gamesObservedIds;
}
