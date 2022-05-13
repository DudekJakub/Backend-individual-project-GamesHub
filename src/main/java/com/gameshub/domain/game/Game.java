package com.gameshub.domain.game;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(
        name = "Game.retrieveGamesWhereNameIsLike",
        query = "SELECT G.ID, G.NAME FROM GAMES G WHERE NAME LIKE CONCAT('%', :NAME, '%')")
@NamedEntityGraph(
        name = "graph.Game.gameOpinions",
        attributeNodes = @NamedAttributeNode("gameOpinions"))
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAMES")
public class Game {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @EqualsAndHashCode.Exclude
    @Column(name = "NAME")
    private String name;

    @EqualsAndHashCode.Exclude
    @OneToMany(targetEntity = GameOpinion.class,
               mappedBy = "game",
               cascade = CascadeType.PERSIST,
               fetch = FetchType.LAZY)
    private final List<GameOpinion> gameOpinions = new ArrayList<>();
}
