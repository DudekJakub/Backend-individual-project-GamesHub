package com.gameshub.domain.game;

import com.gameshub.domain.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAME_RATINGS")
public class GameRating {

    @Id
    @EqualsAndHashCode.Include
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @EqualsAndHashCode.Exclude
    @Column(name = "GAME_NAME")
    private String gameName;

    @NotNull
    @EqualsAndHashCode.Exclude
    @Column(name = "RATING")
    private int rating;

    @NotNull
    @EqualsAndHashCode.Exclude
    @Column(name = "PUBLICATION_DATE")
    private final LocalDateTime publicationDate = LocalDateTime.now();


    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    @JoinColumn(name = "GAME_ID")
    private Game game;


    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;
}
