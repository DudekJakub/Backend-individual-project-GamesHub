package com.gameshub.domain.game;

import com.gameshub.domain.user.User;
import lombok.*;

import javax.persistence.*;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;

@NamedNativeQuery(name = "GameRating.retrieveSixLatestGameRatingsForGame",
                  query = "SELECT * FROM GAME_RATINGS WHERE GAME_ID = :GAME_ID ORDER BY PUBLICATION_DATE DESC LIMIT 6",
                  resultSetMapping = "GameRatingMapping")
@SqlResultSetMapping(
        name = "GameRatingMapping",
        entities = @EntityResult(
                entityClass = GameRating.class,
                fields = {
                        @FieldResult(name = "id", column = "ID"),
                        @FieldResult(name = "game", column = "GAME_ID"),
                        @FieldResult(name = "user", column = "USER_ID"),
                        @FieldResult(name = "gameName", column = "GAME_NAME"),
                        @FieldResult(name = "rating", column = "RATING"),
                        @FieldResult(name = "publicationDate", column = "PUBLICATION_DATE")
                }))
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAME_RATINGS")
public class GameRating implements GameDataSource {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull
    private String gameName;

    @NotNull
    private double rating;

    @NotNull
    private final LocalDateTime publicationDate = LocalDateTime.now();

    @ManyToOne(targetEntity = Game.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "USER_ID")
    private User user;
}
