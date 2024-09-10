package dev.lunyov.petprojectsql.service;

import dev.lunyov.petprojectsql.controller.UserController;
import dev.lunyov.petprojectsql.dto.CreateUserByEmailReq;
import dev.lunyov.petprojectsql.entity.User;
import dev.lunyov.petprojectsql.repositoryWrapper.UserRepositoryWrapper;
import dev.lunyov.petprojectsql.util.UserState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepositoryWrapper userRepositoryWrapper;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepositoryWrapper userRepositoryWrapper,
                       EmailService emailService,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder) {
        this.userRepositoryWrapper = userRepositoryWrapper;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUserByEmail(CreateUserByEmailReq request) {
        Optional<User> existingUser = userRepositoryWrapper.findByLogin(request.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with this email already exists.");
        }
        User user = new User();
        user.setLogin(request.getEmail());
        user.setCreateDateTime(LocalDateTime.now());
        user.setLastModificationDateTime(LocalDateTime.now());
        user.setDescription(request.getDescription());
        user.setState(UserState.CHANGE_PASSWORD);
        user.setPassword(new BCryptPasswordEncoder().encode("defaultPassword"));
        userRepositoryWrapper.save(user);


        String token = jwtService.generateJwtToken(request.getEmail());
        String registrationLink = "http://localhost:8096/register?token=" + token;

        emailService.sendEmail(
                request.getEmail(),
                "Registration in iDivizer",
                "Your email is added to iDivizer, please use the link to register: " + registrationLink
        );
    }

    public void completeRegistration(String token, String newPassword) {
        logger.debug("Received token for registration: " + token);

        // Витяг email з токена
        String email;
        try {
            email = jwtService.extractEmail(token);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid JWT token format during registration.", e);
            throw e;
        }
        logger.debug("Extracted email from token: " + email);

        // Пошук користувача за email
        User user = userRepositoryWrapper.findByLogin(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: " + email);
                    return new RuntimeException("User not found");
                });

        logger.debug("Found user for registration: " + user.getLogin());

        // Оновлення даних користувача
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        user.setState(UserState.ACTIVE);
        user.setLastModificationDateTime(LocalDateTime.now());

        // Спроба зберегти користувача
        try {
            userRepositoryWrapper.save(user);
            logger.info("User registered successfully: " + user.getLogin());
        } catch (Exception e) {
            logger.error("Failed to register user: " + user.getLogin(), e);
            throw new RuntimeException("Failed to register user", e);
        }
    }

    public Optional<User> findByLogin(String login) {
        return userRepositoryWrapper.findByLogin(login);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositoryWrapper.save(user);
    }
}