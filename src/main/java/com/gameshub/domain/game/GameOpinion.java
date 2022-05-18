package com.gameshub.domain.game;

import com.gameshub.domain.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedEntityGraph(
        name = "graph.GameOpinion.user",
        attributeNodes = @NamedAttributeNode("user"))
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAME_OPINIONS")
public class GameOpinion {

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
