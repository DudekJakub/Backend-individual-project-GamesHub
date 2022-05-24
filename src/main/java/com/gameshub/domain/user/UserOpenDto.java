package com.gameshub.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOpenDto {

    private String firstname;
    private String lastname;
    private LocalDateTime registeredDate;
    private String email;
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
