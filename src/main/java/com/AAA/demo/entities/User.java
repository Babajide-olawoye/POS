package com.AAA.demo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Entity representing an application user.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    /** Identifier for the user. */
    private Long id;

    /** Full name of the user. */
    @Column(nullable = false)
    private String name;

    /** Unique email address used for login. */
    @Column(nullable = false, unique = true)
    private String email;

    /** BCrypt hashed password. */
    @Column(nullable = false)
    private String passwordHash;

    /** Associated {@link Role} for this user. */
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /** Date the user was created. */
    @Column
    private LocalDate startDate = LocalDate.now();

    /** Last time the user was updated. */
    @Column(nullable = false, name = "last_updated")
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
