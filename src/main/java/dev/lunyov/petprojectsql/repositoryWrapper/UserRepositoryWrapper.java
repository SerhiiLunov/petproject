package dev.lunyov.petprojectsql.repositoryWrapper;

import dev.lunyov.petprojectsql.entity.User;
import dev.lunyov.petprojectsql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryWrapper {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryWrapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public Optional<User> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}