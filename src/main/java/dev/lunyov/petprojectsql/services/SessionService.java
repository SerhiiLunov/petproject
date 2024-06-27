package dev.lunyov.petprojectsql.services;

import dev.lunyov.petprojectsql.models.Session;
import dev.lunyov.petprojectsql.models.Users;
import dev.lunyov.petprojectsql.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Optional<Object> findByToken(String token) {
        return sessionRepository.findByToken(token);
    }

    public Session createSession(Users user, String token, LocalDateTime expiresAt) {
        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(expiresAt);
        return sessionRepository.save(session);
    }

    public Session refreshSession(String token) {
        Session session = (Session) sessionRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setExpiresAt(LocalDateTime.now().plusHours(1));
        return sessionRepository.save(session);
    }
}