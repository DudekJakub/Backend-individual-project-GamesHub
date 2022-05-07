package com.gameshub.domain.user;

import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "LOGIN_NAME", unique = true)
    private String loginName;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ROLES")
    private AppUserRole appUserRole;

    @Column(name = "REGISTERED_DATE")
    private final LocalDateTime registeredDate = LocalDateTime.now();

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @NonNull
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotNull
    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "ACTIVE")
    private boolean active;

    @NotNull
    @Column(name = "VERIFIED")
    private boolean verified;

    @ManyToMany(
            targetEntity = Game.class,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "USERS_HAVE_GAMES",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "GAME_ID", referencedColumnName = "ID")}
    )
    private final List<Game> games = new ArrayList<>();

    @OneToMany(
            targetEntity = GameOpinion.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private final List<GameOpinion> gameOpinions = new ArrayList<>();
}
