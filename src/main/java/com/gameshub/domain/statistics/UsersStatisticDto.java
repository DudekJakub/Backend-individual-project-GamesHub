package com.gameshub.domain.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsersStatisticDto implements StatisticResource {

    private final String statName = "Users Statistics";

    private Long statId;
    private LocalDateTime addedDate;
    private int qnt;
    private int confirmed;
    private int regularUsers;
    private int admins;
    private int withLowActivityStatus;
    private int withMediumActivityStatus;
    private int withHighActivityStatus;
    private int withTopActivityStatus;
    private int withOpinionsQntLowerThen5;
    private int withOpinionsQntBetween5And10;
    private int withOpinionsQntHigherThen10;
    private int withRatingsQntLowerThen5;
    private int withRatingsQntBetween5And10;
    private int withRatingsQntHigherThen10;

    @Override
    public List<Integer> getAllStatValues() {
        return List.of(statId.intValue(), qnt, confirmed, regularUsers, admins,
                       withLowActivityStatus, withMediumActivityStatus, withHighActivityStatus, withTopActivityStatus,
                       withOpinionsQntLowerThen5, withOpinionsQntBetween5And10, withOpinionsQntHigherThen10,
                       withRatingsQntLowerThen5, withRatingsQntBetween5And10, withRatingsQntHigherThen10);
    }


}
