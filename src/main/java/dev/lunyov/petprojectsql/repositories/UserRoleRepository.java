package dev.lunyov.petprojectsql.repositories;

import dev.lunyov.petprojectsql.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}