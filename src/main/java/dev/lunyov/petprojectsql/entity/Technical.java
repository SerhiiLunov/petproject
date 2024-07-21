package dev.lunyov.petprojectsql.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
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
}