package dev.lunyov.petprojectsql.repository;

import dev.lunyov.petprojectsql.entity.Session;
import dev.lunyov.petprojectsql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
        Optional<Session> findByToken(String token);
        Optional<Session> findByUserAndExpiresAtAfter(User user, LocalDateTime currentTime);
    }