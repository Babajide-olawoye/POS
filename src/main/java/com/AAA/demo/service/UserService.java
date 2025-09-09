package com.AAA.demo.service;

import com.AAA.demo.dto.UserDto;
import com.AAA.demo.entities.User;
import com.AAA.demo.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromUser)
                .toList();
    }

    /**
     * Looks up a {@link User} by id.
     */
    public Optional<UserDto> getById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::fromUser);
    }

    /**
     * Creates a new user after hashing the supplied password.
     */
    public UserDto create(UserDto dto) {
        String hashed = dto.password() != null
                ? passwordEncoder.encode(dto.password())
                : null;

        User entity = dto.toUser();
        entity.setPasswordHash(hashed);

        // timestamps
        if (entity.getStartDate() == null) {
            entity.setStartDate(LocalDate.now());
        }
        entity.setUpdatedDate(LocalDate.now());

        User saved = userRepository.save(entity);
        return UserDto.fromUser(saved);
    }


    /**
     * Deletes a user by id.
     */
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }
}
