package dev.lunyov.petprojectsql.services;

import dev.lunyov.petprojectsql.models.Users;
import dev.lunyov.petprojectsql.models.Role;
import dev.lunyov.petprojectsql.models.Permission;
import dev.lunyov.petprojectsql.models.Session;
import dev.lunyov.petprojectsql.repositories.UserRepository;
import dev.lunyov.petprojectsql.repositories.RoleRepository;
import dev.lunyov.petprojectsql.repositories.PermissionRepository;
import dev.lunyov.petprojectsql.repositories.SessionRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;


    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final SessionRepository sessionRepository;
    private final SecretKey secretKey;
    private final String jwtIssuer;
    private final long jwtExpirationMs;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PermissionRepository permissionRepository, SessionRepository sessionRepository,
                       SecretKey secretKey, @Value("${jwt.issuer}") String jwtIssuer,
                       @Value("${jwt.expiration}") long jwtExpirationMs) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.sessionRepository = sessionRepository;
        this.secretKey = secretKey;
        this.jwtIssuer = jwtIssuer;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    // User-related methods
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(Users user) {
        userRepository.save(user);
    }

    public boolean checkPassword(Users user, String rawPassword) {
        return user.getPassword().equals(rawPassword);
    }

    // Role-related methods
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    // Permission-related methods
    public Permission findPermissionByName(String name) {
        return permissionRepository.findByName(name);
    }

    // Session-related methods
    public Optional<Object> findSessionByToken(String token) {
        return sessionRepository.findByToken(token);
    }

    public Session createSession(Users user, String token, LocalDateTime expiresAt) {
        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(expiresAt);
        return sessionRepository.save(session);
    }

    public Session refreshSession(String token) {
        Session session = (Session) sessionRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setExpiresAt(LocalDateTime.now().plusHours(1));
        return sessionRepository.save(session);
    }

    // Authentication-related methods
    public Session authenticate(String email, String password) {
        Users user = findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!checkPassword(user, password)) {
            throw new RuntimeException("Invalid password");
        }

        String token = generateToken(user);
        return createSession(user, token, LocalDateTime.now().plusHours(1));
    }

    private String generateToken(Users user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}