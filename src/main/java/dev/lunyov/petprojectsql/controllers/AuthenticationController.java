package dev.lunyov.petprojectsql.controllers;

import dev.lunyov.petprojectsql.models.Session;
import dev.lunyov.petprojectsql.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Session> login(@RequestParam String email, @RequestParam String password) {
        try {
            Session session = authenticationService.authenticate(email, password);
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}