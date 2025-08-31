package com.AAA.demo.service;

import com.AAA.demo.entities.User;
import com.AAA.demo.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for working with {@link User} entities.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates the service with required dependencies.
     *
     * @param userRepository repository for user persistence
     * @param passwordEncoder encoder for hashing passwords
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves all users from the database.
     */
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    /**
     * Looks up a {@link User} by id.
     */
    public Optional<User> getById(Long id) {
        return this.userRepository.findById(id);
    }

    /**
     * Creates a new user after hashing the supplied password.
     */
    public User create(User user) {
        String hashedPassword = this.passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        return this.userRepository.save(user);
    }

    /**
     * Deletes a user by id.
     */
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }
}
