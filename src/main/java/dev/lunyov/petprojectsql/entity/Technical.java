package dev.lunyov.petprojectsql.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;


@Setter
@Getter
@Entity
@Table(name = "technical")
public class Technical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "jwt_secret")
    private String jwtSecret;

    @Column(name = "jwt_issuer")
    private String jwtIssuer;

    @Column(name = "jwt_expiration_ms")
    private Integer jwtExpirationMs;
}