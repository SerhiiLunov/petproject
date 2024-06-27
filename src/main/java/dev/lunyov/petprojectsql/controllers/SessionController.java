package dev.lunyov.petprojectsql.controllers;

import dev.lunyov.petprojectsql.models.Session;
import dev.lunyov.petprojectsql.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final AuthenticationService authenticationService;

    @Autowired
    public SessionController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<Session> refreshSession(@RequestParam String token) {
        try {
            Session refreshedSession = authenticationService.refreshSession(token);
            return ResponseEntity.ok(refreshedSession);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}