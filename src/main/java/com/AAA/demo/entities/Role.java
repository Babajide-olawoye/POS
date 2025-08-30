package com.AAA.demo.entities;

import jakarta.persistence.*;

/**
 * Entity representing a user role within the system.
 * <p>
 * Roles are stored in the {@code roles} table and are used to
 * grant authorities to {@link com.AAA.demo.entities.User} instances.
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    /** Identifier for the role. */
    private Long id;

    /**
     * Name of the role. Must be unique and one of Admin, Technician or User.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Validates and sets the role name.
     *
     * @param name proposed role name
     */
    public void setName(String name) {
        if(name.equalsIgnoreCase("Admin") || name.equalsIgnoreCase("Technician") || name.equalsIgnoreCase("User"))
            this.name = name;
        else
            this.name = "Invalid";
    }

}
