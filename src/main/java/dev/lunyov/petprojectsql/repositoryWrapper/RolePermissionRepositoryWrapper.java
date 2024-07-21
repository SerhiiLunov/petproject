package dev.lunyov.petprojectsql.repositoryWrapper;

import dev.lunyov.petprojectsql.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolePermissionRepositoryWrapper {
    private final RolePermissionRepository rolePermissionRepository;

    @Autowired
    public RolePermissionRepositoryWrapper(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }
}
