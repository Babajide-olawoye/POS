package com.AAA.demo.controller;

import com.AAA.demo.dto.UserDto;
import com.AAA.demo.entities.User;
import com.AAA.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller exposing CRUD operations for {@link User} resources.
 */
@RestController
@RequestMapping({"/api/users"})
@Validated
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
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    /**
     * Retrieves a user by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new user record.
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        UserDto createdUser = this.userService.create(userDto);
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
