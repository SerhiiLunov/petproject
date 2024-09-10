package dev.lunyov.petprojectsql.entity;

import dev.lunyov.petprojectsql.util.UserState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Setter
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")  // Встановлюємо кастомний генератор для UUID
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "create_date_time", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "last_modification_date_time", nullable = false)
    private LocalDateTime lastModificationDateTime;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private UserState state;

    @Column(name = "password")
    private String password;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        id = UUID.randomUUID();
        createDateTime = now;
        lastModificationDateTime = now;
    }

    @PreUpdate
    protected void onUpdate() {
        lastModificationDateTime = LocalDateTime.now();
    }
}