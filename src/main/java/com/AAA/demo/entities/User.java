package com.AAA.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

/**
 * Entity representing an application user.
 */
@Table("users")
public class User {
    /** Identifier for the user. */
    @Id
    private Long id;

    /** Full name of the user. */
    private String name;

    /** Unique email address used for login. */
    private String email;

    /** BCrypt hashed password. */
    @Column("password_hash")
    private String passwordHash;

    /** Identifier of the associated role. */
    @Column("role_id")
    private Long roleId;

    /** Date the user was created. */
    @Column("start_date")
    private LocalDate startDate = LocalDate.now();

    /** Last time the user was updated. */
    @Column("last_updated")
    private LocalDate updatedDate = LocalDate.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Sets the BCrypt hashed password for the user.
     *
     * @param hashedPassword a BCrypt hashed value
     */
    public void setPasswordHash(String hashedPassword) {
        this.passwordHash = hashedPassword;
    }

    /**
     * Returns the stored BCrypt hashed password.
     *
     * @return hashed password
     */
    public CharSequence getPasswordHash() {
        return passwordHash;
    }
}
