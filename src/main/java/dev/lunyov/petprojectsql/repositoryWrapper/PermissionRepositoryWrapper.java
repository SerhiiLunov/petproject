package dev.lunyov.petprojectsql.repositoryWrapper;

import dev.lunyov.petprojectsql.entity.Permission;
import dev.lunyov.petprojectsql.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepositoryWrapper {
    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionRepositoryWrapper(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission findByName(String name) {
        return permissionRepository.findByName(name);
    }
}
