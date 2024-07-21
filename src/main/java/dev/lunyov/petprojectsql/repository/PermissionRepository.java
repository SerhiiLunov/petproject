package dev.lunyov.petprojectsql.repository;

import dev.lunyov.petprojectsql.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    PermissionEntity findByName(String name);
}