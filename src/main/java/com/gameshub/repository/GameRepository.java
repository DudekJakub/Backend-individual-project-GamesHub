package com.gameshub.repository;

import com.gameshub.domain.game.Game;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query
    List<Long> retrieveGamesWhereNameIsLike(@Param("NAME") String name);

    @Override
    @EntityGraph(value = "graph.Game.gameOpinions")
    Optional<Game> findById(Long aLong);

    @Override
    List<Game> findAll();
}
