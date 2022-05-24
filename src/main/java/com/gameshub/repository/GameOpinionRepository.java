package com.gameshub.repository;

import com.gameshub.domain.game.GameOpinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GameOpinionRepository extends JpaRepository<GameOpinion, Long> {

    @Query
    List<GameOpinion> retrieveThreeLatestGameOpinionsForGame(@Param("GAME_ID") Long gameId);

    @Override
    Optional<GameOpinion> findById(Long id);
}
