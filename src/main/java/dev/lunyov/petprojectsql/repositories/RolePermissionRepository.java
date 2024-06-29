package dev.lunyov.petprojectsql.repositories;

import dev.lunyov.petprojectsql.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}