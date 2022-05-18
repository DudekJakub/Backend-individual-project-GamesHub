package com.gameshub.repository;

import com.gameshub.domain.Profanity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfanityRepository extends JpaRepository<Profanity, Long> {
}
