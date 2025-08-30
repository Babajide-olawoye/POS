package com.AAA.demo.controller;

import com.AAA.demo.dto.UserDto;
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
    private final UserService userService;
    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    /**
     * Returns a list of all users.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return this.userService.getAll().stream().map(UserDto::fromUser).toList();
    }

    /**
     * Retrieves a user by id.
     */
    @GetMapping({"/{id}"})
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = this.userService.getById(id);
        return user.map(UserDto::fromUser)
                   .map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new user record.
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User createdUser = this.userService.create(userDto.toUser());
        return ResponseEntity.ok(UserDto.fromUser(createdUser));
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
