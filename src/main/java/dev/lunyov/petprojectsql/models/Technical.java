package dev.lunyov.petprojectsql.models;
import jakarta.persistence.*;

@Entity
@Table(name = "technical")
public class Technical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jwt_secret")
    private String jwtSecret;

    @Column(name = "jwt_issuer")
    private String jwtIssuer;

    @Column(name = "jwt_expiration_ms")
    private Integer jwtExpirationMs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public void setJwtIssuer(String jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

    public Integer getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public void setJwtExpirationMs(Integer jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
    }
}