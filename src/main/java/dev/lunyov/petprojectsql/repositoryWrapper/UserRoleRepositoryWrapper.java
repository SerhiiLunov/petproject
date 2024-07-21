package dev.lunyov.petprojectsql.repositoryWrapper;

import dev.lunyov.petprojectsql.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepositoryWrapper {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleRepositoryWrapper(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }
}