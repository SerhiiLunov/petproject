package dev.lunyov.petprojectsql.repository;

import dev.lunyov.petprojectsql.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
}