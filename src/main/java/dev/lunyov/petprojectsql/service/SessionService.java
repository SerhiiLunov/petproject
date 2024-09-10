package dev.lunyov.petprojectsql.service;

import dev.lunyov.petprojectsql.entity.Session;
import dev.lunyov.petprojectsql.entity.User;
import dev.lunyov.petprojectsql.repositoryWrapper.SessionRepositoryWrapper;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;

@Service
public class SessionService {
    private final SessionRepositoryWrapper sessionRepositoryWrapper;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;
    private final JwtBuilder jwtBuilder;
    private final long jwtExpirationMs;
    private final UserService userService;
    private final JwtService jwtService;

    @Value("${jwt.issuer}")
    private String jwtIssuer;


    public SessionService(SessionRepositoryWrapper sessionRepositoryWrapper, PasswordEncoder passwordEncoder,
                          SecretKey secretKey, JwtBuilder jwtBuilder, @Value("${jwt.expiration}") long jwtExpirationMs,
                          @Value("${jwt.issuer}") String jwtIssuer, UserService userService, JwtService jwtService) {
        this.sessionRepositoryWrapper = sessionRepositoryWrapper;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
        this.jwtBuilder = jwtBuilder;
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtIssuer = jwtIssuer;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public Session createSession(User user, String token, LocalDateTime expiresAt) {
        // Перевірка на існуючу сесію для користувача
        sessionRepositoryWrapper.findActiveSessionByUser(user)
                .ifPresent(sessionRepositoryWrapper::delete);

        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(expiresAt);
        return sessionRepositoryWrapper.save(session);
    }

    public Session refreshSession(String token) {
        Session session = sessionRepositoryWrapper.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setExpiresAt(LocalDateTime.now().plusHours(1));
        return sessionRepositoryWrapper.save(session);
    }

    public Session authenticate(String email, String password) {
        User user = userService.findByLogin(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!checkPassword(user, password)) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateJwtToken(String.valueOf(user));
        return createSession(user, token, LocalDateTime.now().plusHours(1));
    }
//    продивитися цей метод

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}