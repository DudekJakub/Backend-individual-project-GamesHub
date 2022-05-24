package com.gameshub.domain.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import java.time.LocalDateTime;

@NamedNativeQuery(name = "GamesStatistic.retrieveTheNewestStats",
                  query = "SELECT * FROM GAMES_STATISTICS ORDER BY ADDED_DATE DESC LIMIT 1",
                  resultClass = GamesStatistic.class)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAMES_STATISTICS")
public class GamesStatistic {

    @Id
    @Column(name = "ID", unique = true)
    private Long id;
    private LocalDateTime addedDate = LocalDateTime.now();
    private int qnt;
    private int opinionsQnt;
    private int withLowPopStatus;
    private int withMediumPopStatus;
    private int withHighPopStatus;
    private int withVeryHighPopStatus;
    private int withOnTheTopPopStatus;

    @Column(name = "WITH_AVG_RATING_LOWER_THEN_FIVE")
    private int withAvgRatingLowerThenFive;

    @Column(name = "WITH_AVG_RATING_BETWEEN_5_AND_7_5")
    private int withAvgRatingBetween5And7_5;

    @Column(name = "WITH_AVG_RATING_BETWEEN_7_5_AND_9")
    private int withAvgRatingBetween7_5And8_9;

    @Column(name = "WITH_AVG_RATING_HIGHER_EQUALS_9")
    private int withAvgRatingHigherThen9;
}
