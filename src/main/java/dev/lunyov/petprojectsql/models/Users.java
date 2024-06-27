package dev.lunyov.petprojectsql.models;
import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "user")
    private Set<Session> sessions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }
}