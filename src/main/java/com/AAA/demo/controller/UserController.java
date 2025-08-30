package com.AAA.demo.controller;

import com.AAA.demo.entities.User;
import com.AAA.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller exposing CRUD operations for {@link User} resources.
 */
@RestController
@RequestMapping({"/api/users"})
public class UserController {
    @Autowired
    private UserService userService;

    public UserController() {
    }

    /**
     * Returns a list of all users.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.getAll();
    }

    /**
     * Retrieves a user by id.
     */
    @GetMapping({"/{id}"})
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = this.userService.getById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new user record.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = this.userService.create(user);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Deletes the specified user.
     */
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
