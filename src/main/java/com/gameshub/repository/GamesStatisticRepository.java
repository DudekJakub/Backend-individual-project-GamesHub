package com.gameshub.repository;

import com.gameshub.domain.statistics.GamesStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GamesStatisticRepository extends JpaRepository<GamesStatistic, Long> {

    @Procedure(procedureName = "CreateNewGamesStats")
    void createNewGamesStats();

    @Query
    Optional<GamesStatistic> retrieveTheNewestStats();

    @Override
    List<GamesStatistic> findAll();
}
