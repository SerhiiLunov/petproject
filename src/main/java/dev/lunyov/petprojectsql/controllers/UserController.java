package dev.lunyov.petprojectsql.controllers;

import dev.lunyov.petprojectsql.entity.Session;
import dev.lunyov.petprojectsql.entity.Users;
import dev.lunyov.petprojectsql.dto.AddPartnerReq;
import dev.lunyov.petprojectsql.dto.AddParthnerResp;
import dev.lunyov.petprojectsql.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add-partner")
    @Role("user_creation")
    public AddParthnerResp addPartner(@RequestParam AddPartnerReq addParthnerReq) {
        if (userService.findByEmail(addParthnerReq.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Користувач з таким email вже існує");
        }

        Users partner = new Users();
        partner.setEmail(email);
        partner.setPassword("default-password");

        userService.save(partner);

        return ResponseEntity.ok("Партнерський користувач з email " + email + " успішно доданий");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        Users user = userService.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Користувач з таким email не знайдений");
        }

        user.setPassword("new-password");
        userService.save(user);

        return ResponseEntity.ok("Пароль для користувача з email " + email + "скинуто");
    }

    @PostMapping("/login")
    public ResponseEntity<Session> login(@RequestParam String email, @RequestParam String password) {
        try {
            Session session = userService.authenticate(email, password);
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/refresh-session")
    public ResponseEntity<Session> refreshSession(@RequestParam String token) {
        try {
            Session refreshedSession = userService.refreshSession(token);
            return ResponseEntity.ok(refreshedSession);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/test")
    @Role("test_url")



}