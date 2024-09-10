package dev.lunyov.petprojectsql.repositoryWrapper;

import dev.lunyov.petprojectsql.entity.Session;
import dev.lunyov.petprojectsql.entity.User;
import dev.lunyov.petprojectsql.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class SessionRepositoryWrapper {
        private final SessionRepository sessionRepository;

        @Autowired
        public SessionRepositoryWrapper(SessionRepository sessionRepository) {
            this.sessionRepository = sessionRepository;
        }

        public Optional<Session> findByToken(String token) {
            return sessionRepository.findByToken(token);
        }

        public Session save(Session session) {
            return sessionRepository.save(session);
        }

        public Optional<Session> findActiveSessionByUser(User user) {
            return sessionRepository.findByUserAndExpiresAtAfter(user, LocalDateTime.now());
        }

        public void delete(Session session) {
            sessionRepository.delete(session);
        }
    }