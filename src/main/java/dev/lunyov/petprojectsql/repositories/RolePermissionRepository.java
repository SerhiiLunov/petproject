package dev.lunyov.petprojectsql.repositories;

import dev.lunyov.petprojectsql.models.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}