package com.gameshub.domain.notification;

import com.gameshub.domain.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "APPLICATION_NOTIFICATIONS")
public class AppNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Column(name = "GAME_ID")
    private Long referencedGameId;

    @NotNull
    @Column(name = "REFERENCED_EVENT_ID")
    private Long referencedEventId;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private NotifStatus systemStatus;

    @EqualsAndHashCode.Include
    @NotNull
    private final LocalDateTime notificationDate = LocalDateTime.now();

    @ManyToMany(targetEntity = User.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "USERS_HAVE_APP_NOTIFICATIONS",
              joinColumns =        {@JoinColumn(name = "NOTIFICATION_ID", referencedColumnName = "ID")},
              inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")})
    private final Set<User> notificatedUsers = new HashSet<>();
}
