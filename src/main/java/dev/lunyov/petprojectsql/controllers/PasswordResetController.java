package dev.lunyov.petprojectsql.controllers;

import dev.lunyov.petprojectsql.models.Users;
import dev.lunyov.petprojectsql.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    private final UserService userService;

    @Autowired
    public PasswordResetController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        Users user = userService.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Користувач з таким email не знайдений");
        }

        user.setPassword("new-password");
        userService.save(user);

        return ResponseEntity.ok("Пароль для користувача з email " + email + " скинуто");
    }
}