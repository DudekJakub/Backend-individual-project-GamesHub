package com.gameshub.repository;

import com.gameshub.domain.statistics.UsersStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersStatisticRepository extends JpaRepository<UsersStatistic, Long> {

    @Procedure(procedureName = "CreateNewUsersStats")
    void createNewUsersStats();

    @Query
    Optional<UsersStatistic> retrieveTheNewestStats();

    @Override
    List<UsersStatistic> findAll();
}
