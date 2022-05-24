package com.gameshub.domain.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedNativeQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NamedNativeQuery(name = "UsersStatistic.retrieveTheNewestStats",
                  query = "SELECT * FROM USERS_STATISTICS ORDER BY ADDED_DATE DESC LIMIT 1",
                  resultClass = UsersStatistic.class)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USERS_STATISTICS")
public class UsersStatistic {

    @Id
    @Column(name = "ID", unique = true)
    private Long id;
    private LocalDateTime addedDate = LocalDateTime.now();
    private int qnt;
    private int confirmed;
    private int regularUsers;
    private int admins;
    private int withLowActivityStatus;
    private int withMediumActivityStatus;
    private int withHighActivityStatus;
    private int withTopActivityStatus;

    @Column(name = "WITH_OPINIONS_QNT_LOWER_THEN_5")
    private int withOpinionsQntLowerThen5;

    @Column(name = "WITH_OPINIONS_QNT_BETWEEN_5_AND_10")
    private int withOpinionsQntBetween5And10;

    @Column(name = "WITH_OPINIONS_QNT_HIGHER_THEN_10")
    private int withOpinionsQntHigherThen10;

    @Column(name = "WITH_RATINGS_QNT_LOWER_THEN_5")
    private int withRatingsQntLowerThen5;

    @Column(name = "WITH_RATINGS_QNT_BETWEEN_5_AND_10")
    private int withRatingsQntBetween5And10;

    @Column(name = "WITH_RATINGS_QTN_HIGHER_THEN_10")
    private int withRatingsQntHigherThen10;
}
