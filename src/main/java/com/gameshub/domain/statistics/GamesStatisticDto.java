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
public class GamesStatisticDto implements StatisticResource {

    private final String statName = "Games Statistics";

    private Long statId;
    private LocalDateTime addedDate;
    private int qnt;
    private int opinionsQnt;
    private int withLowPopStatus;
    private int withMediumPopStatus;
    private int withHighPopStatus;
    private int withVeryHighPopStatus;
    private int withOnTheTopPopStatus;
    private int withAvgRatingLowerThenFive;
    private int withAvgRatingBetween5And7_5;
    private int withAvgRatingBetween7_5And8_9;
    private int withAvgRatingHigherThen9;

    public List<Integer> getAllStatValues() {
        return List.of(statId.intValue(), qnt, opinionsQnt, withLowPopStatus, withMediumPopStatus, withHighPopStatus,
                withVeryHighPopStatus, withOnTheTopPopStatus, withAvgRatingLowerThenFive, withAvgRatingBetween5And7_5,
                withAvgRatingBetween7_5And8_9, withAvgRatingHigherThen9);
    }
}
