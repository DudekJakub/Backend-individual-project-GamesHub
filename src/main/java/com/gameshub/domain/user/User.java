package com.gameshub.domain.user;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "graph.User.games",
                attributeNodes = {
                        @NamedAttributeNode("gameOpinions"),
                        @NamedAttributeNode("gamesOwned"),
                        @NamedAttributeNode("gamesWantedToOwn")
                })})
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USERS")
public class User {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @EqualsAndHashCode.Include
    @Column(name = "LOGIN_NAME", unique = true)
    private String loginName;

    @NotNull
    @EqualsAndHashCode.Exclude
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ROLES")
    private AppUserRole appUserRole;

    @EqualsAndHashCode.Exclude
    @Column(name = "REGISTERED_DATE")
    private final LocalDateTime registeredDate = LocalDateTime.now();

    @NotNull
    @EqualsAndHashCode.Exclude
    @Column(name = "EMAIL")
    private String email;

    @NonNull
    @EqualsAndHashCode.Exclude
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotNull
    @EqualsAndHashCode.Exclude
    @Column(name = "LASTNAME")
    private String lastname;

    @EqualsAndHashCode.Exclude
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @EqualsAndHashCode.Exclude
    @Column(name = "ACTIVE")
    private boolean active;

    @NotNull
    @EqualsAndHashCode.Exclude
    @Column(name = "VERIFIED")
    private boolean verified;


    @EqualsAndHashCode.Exclude
    @OneToMany(targetEntity = GameOpinion.class,
               mappedBy = "user",
               cascade = CascadeType.PERSIST,
               fetch = FetchType.LAZY)
    private final List<GameOpinion> gameOpinions = new ArrayList<>();


    @EqualsAndHashCode.Exclude
    @ManyToMany(targetEntity = Game.class,
                cascade = { CascadeType.PERSIST,
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH
                          },
            fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_HAVE_GAMES",
               joinColumns =        {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
               inverseJoinColumns = {@JoinColumn(name = "GAME_ID", referencedColumnName = "ID")})
    private final Set<Game> gamesOwned = new HashSet<>();


    @EqualsAndHashCode.Exclude
    @ManyToMany(targetEntity = Game.class,
                cascade = { CascadeType.PERSIST,
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH
                           },
                fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_WANT_TO_HAVE_GAMES",
               joinColumns =        {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
               inverseJoinColumns = {@JoinColumn(name = "GAME_ID", referencedColumnName = "ID")})
    private final Set<Game> gamesWantedToOwn = new HashSet<>();
}
