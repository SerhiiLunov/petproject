package dev.lunyov.petprojectsql.repositories;

import dev.lunyov.petprojectsql.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
}