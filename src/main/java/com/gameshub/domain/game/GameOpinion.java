package com.gameshub.domain.game;

import com.gameshub.domain.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAME_OPINIONS")
public class GameOpinion {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "GAME_NAME")
    private String gameName;

    @NotNull
    @Column(name = "OPINION")
    private String opinion;

    @NotNull
    @Column(name = "PUBLICATION_DATE")
    private final LocalDateTime publicationDate = LocalDateTime.now();

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;
}
