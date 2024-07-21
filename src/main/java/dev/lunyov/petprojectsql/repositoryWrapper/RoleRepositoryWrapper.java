package dev.lunyov.petprojectsql.repositoryWrapper;

import dev.lunyov.petprojectsql.entity.Role;
import dev.lunyov.petprojectsql.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryWrapper {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleRepositoryWrapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
