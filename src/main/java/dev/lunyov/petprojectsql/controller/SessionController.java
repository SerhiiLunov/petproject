package dev.lunyov.petprojectsql.controller;

import dev.lunyov.petprojectsql.dto.RefreshSessionReq;
import dev.lunyov.petprojectsql.dto.RefreshSessionResp;
import dev.lunyov.petprojectsql.entity.Session;
import dev.lunyov.petprojectsql.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshSessionResp> refreshSession(@RequestBody RefreshSessionReq request) {
        try {
            Session refreshedSession = sessionService.refreshSession(request.getToken());
            return ResponseEntity.ok(new RefreshSessionResp(refreshedSession));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}