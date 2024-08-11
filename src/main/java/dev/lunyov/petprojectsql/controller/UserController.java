package dev.lunyov.petprojectsql.controller;

import dev.lunyov.petprojectsql.dto.*;
import dev.lunyov.petprojectsql.entity.Session;
import dev.lunyov.petprojectsql.entity.User;
import dev.lunyov.petprojectsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/add-partner")
//    public ResponseEntity<AddPartnerResp> addPartner(@RequestBody AddPartnerReq request) {
//        if (userService.findByEmail(request.getEmail()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new AddPartnerResp("User with this email already exists"));
//        }
//
//        User partner = new User();
//        partner.setEmail(request.getEmail());
//        partner.setPassword("default-password");
//
//        userService.save(partner);
//
//        return ResponseEntity.ok(new AddPartnerResp("Partner user with email " + request.getEmail()
//                + " successfully added"));
//    }

    @PostMapping("/add-partner")
    public String addPartner(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return "Error: Email is required";
        }

        if (userService.findByEmail(email).isPresent()) {
            return "Error: User with this email already exists";
        }

        User partner = new User();
        partner.setEmail(email);
        partner.setPassword("default-password");

        userService.save(partner);

        return "Partner user with email " + email + " successfully added";
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResp> resetPassword(@RequestBody ResetPasswordReq request) {
        User user = userService.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResetPasswordResp("User with this email not found"));
        }

        user.setPassword("new-password");
        userService.save(user);

        return ResponseEntity.ok(new ResetPasswordResp("Password for the user from email "
                + request.getEmail() + " was reset"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResp> login(@RequestBody LoginReq request) {
        try {
            Session session = userService.authenticate(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new LoginResp(session));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/refresh-session")
    public ResponseEntity<RefreshSessionResp> refreshSession(@RequestBody RefreshSessionReq request) {
        try {
            Session refreshedSession = userService.refreshSession(request.getToken());
            return ResponseEntity.ok(new RefreshSessionResp(refreshedSession));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


//    @PostMapping("/test")
//    @Role("test_url")
}