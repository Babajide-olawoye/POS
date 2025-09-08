package com.AAA.demo.dto;

import com.AAA.demo.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Data transfer object for {@link User} entities.
 */
public record UserDto(
        Long id,
        @NotNull(message = "User ID is required")
        String name,
        @NotNull(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,
        @NotNull(message = "Password is required")
        @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
        String password,
        @NotNull(message = "Select a role for this user")
        Long roleId,
        LocalDate startDate,
        LocalDate updatedDate
) {
    /**
     * Creates a DTO from the provided {@link User} entity.
     */
    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                null,
                user.getRoleId(),
                user.getStartDate(),
                user.getUpdatedDate()
        );
    }

    /**
     * Converts this DTO back to a {@link User} entity.
     */
    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setRoleId(roleId);
        if (password != null) {
            user.setPasswordHash(password);
        }
        if (startDate != null) {
            user.setStartDate(startDate);
        }
        if (updatedDate != null) {
            user.setUpdatedDate(updatedDate);
        }
        return user;
    }
}
