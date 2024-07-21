package dev.lunyov.petprojectsql.repository;

import dev.lunyov.petprojectsql.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}