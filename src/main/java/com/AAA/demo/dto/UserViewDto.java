package com.AAA.demo.dto;

import com.AAA.demo.entities.User;

import java.time.LocalDate;

/**
 * Data transfer object representing {@link User} details returned to clients.
 * This DTO is read-only and does not expose password information.
 */
public record UserViewDto(
        Long id,
        String name,
        String email,
        Long roleId,
        LocalDate startDate,
        LocalDate updatedDate
) {
    /**
     * Creates a DTO from the provided {@link User} entity.
     */
    public static UserViewDto fromUser(User user) {
        return new UserViewDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoleId(),
                user.getStartDate(),
                user.getUpdatedDate()
        );
    }
}