package dev.lunyov.petprojectsql.services;

import dev.lunyov.petprojectsql.models.Permission;
import dev.lunyov.petprojectsql.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission findByName(String name) {
        return permissionRepository.findByName(name);
    }
}