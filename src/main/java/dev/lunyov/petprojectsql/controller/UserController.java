package dev.lunyov.petprojectsql.controller;

import dev.lunyov.petprojectsql.dto.*;
import dev.lunyov.petprojectsql.entity.Session;
import dev.lunyov.petprojectsql.entity.User;
import dev.lunyov.petprojectsql.service.SessionService;
import dev.lunyov.petprojectsql.service.UserService;
import dev.lunyov.petprojectsql.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static dev.lunyov.petprojectsql.util.Status.REQUEST_ERROR;
import static dev.lunyov.petprojectsql.util.Status.SUCCESS;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final SessionService sessionService;


    @Autowired
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/create/by-email")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUserByEmail(@RequestBody CreateUserByEmailReq request) {
        try {
            userService.createUserByEmail(request);
            return ResponseEntity.ok().body(new ApiResponse(SUCCESS, "Successful creation of a new User"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(REQUEST_ERROR, e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> completeRegistration(@RequestParam("token") String token, @RequestBody PasswordDto passwordDto) {
        if (!token.matches("^[^.]+\\.[^.]+\\.[^.]+$")) {
            logger.error("Invalid JWT token format provided: " + token);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(REQUEST_ERROR, "Invalid token format"));
        }
        logger.debug("Received registration request with token: " + token);
        try {
            // Передача токена та нового пароля у UserService
            userService.completeRegistration(token, passwordDto.getNewPassword());
            return ResponseEntity.ok(new ApiResponse(SUCCESS, "Successful transfer of token and new password to UserService"));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid token provided: " + token, e);
            return ResponseEntity.badRequest().body(new ApiResponse(REQUEST_ERROR, "Invalid token"));
        } catch (Exception e) {
            logger.error("Error during registration: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(REQUEST_ERROR, "Error during registration"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResp> resetPassword(@RequestBody ResetPasswordReq request) {
        User user = userService.findByLogin(request.getEmail())
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
            Session session = sessionService.authenticate(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new LoginResp(session));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


//    @PostMapping("/test")
//    @Role("test_url")
}