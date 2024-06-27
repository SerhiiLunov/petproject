package dev.lunyov.petprojectsql.services;

import dev.lunyov.petprojectsql.models.Session;
import dev.lunyov.petprojectsql.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public AuthenticationService(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public Session authenticate(String email, String password) {
        Users user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!userService.checkPassword(user, password)) {
            throw new RuntimeException("Invalid password");
        }

        String token = generateToken(user);
        return sessionService.createSession(user, token, LocalDateTime.now().plusHours(1));
    }

    private String generateToken(Users user) {
        // Генерація токена (JWT або будь-який інший спосіб)
        return "generated-token-for-" + user.getEmail();
    }

    public Session refreshSession(String token) {
        return sessionService.refreshSession(token);
    }
}