package com.AAA.demo.dto;

import com.AAA.demo.entities.User;

import java.time.LocalDate;

/**
 * Data transfer object for {@link User} entities.
 */
public record UserDto(
        Long id,
        String name,
        String email,
        String password,
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
