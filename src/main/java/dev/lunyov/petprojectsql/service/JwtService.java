package dev.lunyov.petprojectsql.service;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String secretKey;  // Ваш секретний ключ у форматі Base64

    @Value("${jwt.expiration}")
    private long expirationTime;

    private Key signingKey;

    @PostConstruct
    public void init() {
        logger.info("JWT secret key: " + secretKey);

        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("JWT secret is not set. Please provide a valid secret key.");
        }

        try {
            // Перетворюємо секретний ключ з Base64
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            this.signingKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA512");
            logger.info("Signing key initialized successfully.");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid Base64 key in 'jwt.secret'. Ensure the key is properly encoded in Base64 format.", e);
            throw new IllegalArgumentException("Invalid Base64 key in 'jwt.secret'. Ensure the key is properly encoded in Base64 format.", e);
        }
    }

    public String generateJwtToken(String email) {
        try {
            String token = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(signingKey, SignatureAlgorithm.HS512)
                    .compact();
            logger.info("Generated JWT token for email: " + email + ", Token: " + token);
            return token;
        } catch (Exception e) {
            logger.error("Error generating JWT token for email: " + email, e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public Claims extractClaims(String token) {
        if (token == null || token.split("\\.").length != 3) {
            logger.error("Invalid token format: " + token);
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        try {
            logger.debug("Attempting to extract claims from token: " + token);
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            logger.debug("Successfully extracted claims: " + claims);
            return claims;
        } catch (MalformedJwtException e) {
            logger.error("Malformed JWT token provided: " + token, e);
            throw new IllegalArgumentException("Invalid JWT token format", e);
        } catch (Exception e) {
            logger.error("Failed to extract claims from token: " + token, e);
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }

    public String extractEmail(String token) {
        logger.debug("Attempting to extract email from token: " + token);

        try {
            String email = extractClaims(token).getSubject();
            logger.debug("Successfully extracted email from token: " + email);
            return email;
        } catch (IllegalArgumentException e) {
            logger.error("Failed to extract email from token: " + token, e);
            throw e; // Або ви можете повернути спеціальне значення або обробити помилку інакше
        }
    }
}