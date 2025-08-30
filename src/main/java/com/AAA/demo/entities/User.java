package com.AAA.demo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "users"
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false,
            unique = true
    )
    private String email;
    @Column(
            nullable = false
    )
    private String passwordHash;
    @OneToOne
    @JoinColumn(
            name = "role_id"
    )
    private Role role;
    @Column
    private LocalDate startDate = LocalDate.now();
    @Column(
            nullable = false,
            name = "last_updated"
    )
    private LocalDate updatedDate = LocalDate.now();

    public void setPasswordHash(String hashedPassword) {
        this.passwordHash = hashedPassword;
    }

    public CharSequence getPasswordHash() {
        return passwordHash;
    }
}
