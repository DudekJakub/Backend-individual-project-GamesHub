package com.gameshub.domain.game;

import com.gameshub.domain.user.User;
import lombok.*;

import javax.persistence.*;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAME_RATINGS")
public class GameRating {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull
    private String gameName;

    @NotNull
    private int rating;

    @NotNull
    private final LocalDateTime publicationDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
