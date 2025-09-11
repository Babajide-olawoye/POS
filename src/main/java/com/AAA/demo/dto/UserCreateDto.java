package com.AAA.demo.dto;

import com.AAA.demo.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object used when creating new {@link User} entities.
 */
public record UserCreateDto(
        @NotNull(message = "User name is required")
        String name,
        @NotNull(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,
        @NotNull(message = "Password is required")
        @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
        String password,
        @NotNull(message = "Select a role for this user")
        Long roleId
) {
    /**
     * Converts this DTO to a {@link User} entity.
     */
    public User toUser() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoleId(roleId);
        if (password != null) {
            user.setPasswordHash(password);
        }
        return user;
    }
}
