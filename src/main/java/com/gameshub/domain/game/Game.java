package com.gameshub.domain.game;

import com.gameshub.domain.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NamedNativeQuery(
        name = "Game.retrieveGamesWhereNameIsLike",
        query = "SELECT G.ID, G.NAME FROM GAMES G WHERE NAME LIKE CONCAT('%', :NAME, '%')")
@NamedEntityGraph(
        name = "graph.Game.gameOpinions",
        attributeNodes = @NamedAttributeNode("gameOpinions"))
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAMES")
public class Game {

    @Id
    @EqualsAndHashCode.Include
    @NotNull
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "game")
    private final List<GameOpinion> gameOpinions = new ArrayList<>();

    @ManyToMany(targetEntity = User.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "GAMES_HAVE_OBSERVERS",
               joinColumns =        {@JoinColumn(name = "GAME_ID", referencedColumnName = "ID")},
               inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")})
    private final Set<User> observers = new HashSet<>();

    @ManyToMany(mappedBy = "gamesOwned")
    private final Set<User> usersOwnedThisGame = new HashSet<>();

    @ManyToMany(mappedBy = "gamesWantedToOwn")
    private final Set<User> usersWantedToOwnThisGame = new HashSet<>();
}
