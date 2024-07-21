package dev.lunyov.petprojectsql.repository;

import dev.lunyov.petprojectsql.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Object> findByToken(String token);
}
