package com.gameshub.repository;

import com.gameshub.domain.game.GameRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GameRatingRepository extends JpaRepository<GameRating, Long> {

    @Override
    Optional<GameRating> findById(Long id);

    @Override
    List<GameRating> findAll();
}
