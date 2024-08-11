package dev.lunyov.petprojectsql.service;

import dev.lunyov.petprojectsql.entity.User;
import dev.lunyov.petprojectsql.entity.Role;
import dev.lunyov.petprojectsql.entity.Permission;
import dev.lunyov.petprojectsql.entity.Session;
import dev.lunyov.petprojectsql.repositoryWrapper.PermissionRepositoryWrapper;
import dev.lunyov.petprojectsql.repositoryWrapper.RoleRepositoryWrapper;
import dev.lunyov.petprojectsql.repositoryWrapper.SessionRepositoryWrapper;
import dev.lunyov.petprojectsql.repositoryWrapper.UserRepositoryWrapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepositoryWrapper userRepositoryWrapper;
    private final RoleRepositoryWrapper roleRepositoryWrapper;
    private final PermissionRepositoryWrapper permissionRepositoryWrapper;
    private final SessionRepositoryWrapper sessionRepositoryWrapper;
    private final SecretKey secretKey;
    private final String jwtIssuer;

    @Value("${jwt.expiration}")
    private final long jwtExpirationMs;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepositoryWrapper userRepositoryWrapper,
                       RoleRepositoryWrapper roleRepositoryWrapper,
                       PermissionRepositoryWrapper permissionRepositoryWrapper,
                       SessionRepositoryWrapper sessionRepositoryWrapper,
                       SecretKey secretKey, @Value("${jwt.issuer}") String jwtIssuer,
                       @Value("${jwt.expiration}") long jwtExpirationMs, PasswordEncoder passwordEncoder) {
        this.userRepositoryWrapper = userRepositoryWrapper;
        this.roleRepositoryWrapper = roleRepositoryWrapper;
        this.permissionRepositoryWrapper = permissionRepositoryWrapper;
        this.sessionRepositoryWrapper = sessionRepositoryWrapper;
        this.secretKey = secretKey;
        this.jwtIssuer = jwtIssuer;
        this.jwtExpirationMs = jwtExpirationMs;
        this.passwordEncoder = passwordEncoder;
    }

public String generateJwtToken(String email){
        return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs))
        .signWith(secretKey)
        .compact();
        }

    // User-related methods
    public Optional<User> findByEmail(String email) {
        return userRepositoryWrapper.findByEmail(email);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositoryWrapper.save(user);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    // Role-related methods
    public Role findRoleByName(String name) {
        return roleRepositoryWrapper.findByName(name);
    }

    // Permission-related methods
    public Permission findPermissionByName(String name) {
        return permissionRepositoryWrapper.findByName(name);
    }

    // Session-related methods
    public Optional<Object> findSessionByToken(String token) {
        return sessionRepositoryWrapper.findByToken(token);
    }

    public Session createSession(User user, String token, LocalDateTime expiresAt) {
        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(expiresAt);
        return sessionRepositoryWrapper.save(session);
    }

    public Session refreshSession(String token) {
        Session session = (Session) sessionRepositoryWrapper.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setExpiresAt(LocalDateTime.now().plusHours(1));
        return sessionRepositoryWrapper.save(session);
    }

    // Authentication-related methods
    public Session authenticate(String email, String password) {
        User user = findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!checkPassword(user, password)) {
            throw new RuntimeException("Invalid password");
        }

        String token = generateToken(user);
        return createSession(user, token, LocalDateTime.now().plusHours(1));
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}