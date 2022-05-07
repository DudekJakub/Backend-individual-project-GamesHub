package com.gameshub.domain.game;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAMES")
public class Game {

    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @OneToMany(
            targetEntity = GameOpinion.class,
            mappedBy = "game",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private final List<GameOpinion> gameOpinions = new ArrayList<>();
}
