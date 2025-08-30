package com.AAA.demo.service;

import com.AAA.demo.entities.User;
import com.AAA.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService() {
    }

    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    public Optional<User> getById(Long id) {
        return this.userRepository.findById(id);
    }

    public User create(User user) {
        String hashedPassword = this.passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        return (User)this.userRepository.save(user);
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }
}
