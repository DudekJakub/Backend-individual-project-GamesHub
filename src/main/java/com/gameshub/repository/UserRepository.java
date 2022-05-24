package com.gameshub.repository;

import com.gameshub.domain.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @EntityGraph(value = "graph.User.games")
    Optional<User> findByLoginName(String loginName);

    @Override
    @EntityGraph(value = "graph.User.games")
    Optional<User> findById(Long id);

    @Override
    @EntityGraph(value = "graph.User.games")
    List<User> findAll();

    @Query
    @EntityGraph(value = "graph.User.games")
    List<User> retrieveVerifiedUsers();

    @Query
    List<User> retrieveAdmins();


}
