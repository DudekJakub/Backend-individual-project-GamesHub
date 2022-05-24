package com.gameshub.domain.user;

import com.gameshub.domain.book.Book;
import com.gameshub.domain.game.Game;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.notification.AppNotification;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@NamedQueries({
      @NamedQuery(name = "User.retrieveVerifiedUsers",
                  query = "FROM USERS WHERE VERIFIED = true"),
      @NamedQuery(name = "User.retrieveAdmins",
                  query = "FROM USERS WHERE ROLES = 'ADMIN'")
})
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "graph.User.games",
                attributeNodes = {
                        @NamedAttributeNode("gameOpinions"),
                        @NamedAttributeNode("gamesOwned"),
                        @NamedAttributeNode("gamesWantedToOwn")
                })})
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ROLES")
    private AppUserRole appUserRole;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "USER_NOTIFICATION_STRATEGY")
    private AppUserNotificationStrategy notificationStrategy;

    private final LocalDateTime registeredDate = LocalDateTime.now();

    @NotNull
    private String email;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    private String password;

    @NotNull
    private boolean active;

    @NotNull
    private boolean verified;

    @Column(name = "OPINIONS_QNT")
    private int opinionsQnt;

    @Column(name = "OPINIONS_PER_DAY")
    private double opinionsPerDay;

    @Column(name = "RATINGS_QNT")
    private int ratingsQnt;

    @Column(name = "RATINGS_PER_DAY")
    private double ratingsPerDay;

    @OneToMany(mappedBy = "user")
    private final List<GameOpinion> gameOpinions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<GameRating> gameRatings = new ArrayList<>();

    @ManyToMany(mappedBy = "observers")
    private final Set<Game> observedGames = new HashSet<>();

    @ManyToMany(mappedBy = "notificatedUsers")
    private final Set<AppNotification> appNotifications = new HashSet<>();

    @ManyToMany(targetEntity = Game.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "USERS_HAVE_GAMES",
               joinColumns =        {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
               inverseJoinColumns = {@JoinColumn(name = "GAME_ID", referencedColumnName = "ID")})
    private final Set<Game> gamesOwned = new HashSet<>();


    @ManyToMany(targetEntity = Game.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "USERS_WANT_TO_HAVE_GAMES",
               joinColumns =        {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
               inverseJoinColumns = {@JoinColumn(name = "GAME_ID", referencedColumnName = "ID")})
    private final Set<Game> gamesWantedToOwn = new HashSet<>();


    @ManyToMany(targetEntity = Book.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "USERS_MEMORIZED_BOOKS",
               joinColumns =        {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
               inverseJoinColumns = {@JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")})
    private final Set<Book> booksMemorized = new HashSet<>();
}
