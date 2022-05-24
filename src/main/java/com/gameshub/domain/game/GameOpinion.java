package com.gameshub.domain.game;

import com.gameshub.domain.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedNativeQueries({
       @NamedNativeQuery(name = "GameOpinion.retrieveThreeLatestGameOpinionsForGame",
                        query = "SELECT * FROM GAME_OPINIONS WHERE GAME_ID = :GAME_ID ORDER BY PUBLICATION_DATE DESC LIMIT 4",
                        resultSetMapping = "GameOpinionMapping"),
})
@SqlResultSetMapping(
        name = "GameOpinionMapping",
        entities = @EntityResult(
                entityClass = GameOpinion.class,
                fields = {
                        @FieldResult(name = "id", column = "ID"),
                        @FieldResult(name = "game", column = "GAME_ID"),
                        @FieldResult(name = "user", column = "USER_ID"),
                        @FieldResult(name = "gameName", column = "GAME_NAME"),
                        @FieldResult(name = "opinion", column = "OPINION"),
                        @FieldResult(name = "publicationDate", column = "PUBLICATION_DATE"),
                        @FieldResult(name = "userLogin", column = "user_login")
                }))
@NamedEntityGraph(
        name = "graph.GameOpinion.user",
        attributeNodes = @NamedAttributeNode("user"))
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAME_OPINIONS")
public class GameOpinion implements GameDataSource {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull
    private String gameName;

    @NotNull
    @EqualsAndHashCode.Include
    private String userLogin;

    @NotNull
    private String opinion;

    @NotNull
    private final LocalDateTime publicationDate = LocalDateTime.now();

    @ManyToOne(targetEntity = Game.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "USER_ID")
    private User user;
}
