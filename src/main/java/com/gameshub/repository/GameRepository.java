package com.gameshub.repository;

import com.gameshub.domain.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long> {

    @Override
    List<Game> findAll();

    @Override
    Optional<Game> findById(Long aLong);

    @Override
    <S extends Game> S save(S entity);
}
