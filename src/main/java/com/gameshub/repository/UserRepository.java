package com.gameshub.repository;

import com.gameshub.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginName(String loginName);

    Optional<User> findByEmail(String email);

    @Override
    List<User> findAll();

    @Override
    <S extends User> S save(S entity);
}
