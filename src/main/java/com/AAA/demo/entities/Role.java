package com.AAA.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entity representing a user role within the system.
 */
@Table("roles")
public class Role {
    /** Identifier for the role. */
    @Id
    private Long id;

    /**
     * Name of the role. Must be unique and one of Admin, Technician or Cashier.
     */
    private String name;

    /**
     * Validates and sets the role name.
     *
     * @param name proposed role name
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Role name cannot be null");
        }
        if (name.equalsIgnoreCase("Admin")
                || name.equalsIgnoreCase("Technician")
                || name.equalsIgnoreCase("Cashier")) {this.name = name;}
        else {
            throw new IllegalArgumentException("Invalid role name: " + name);
        }
    }

    /**
     * Returns the role identifier.
     *
     * @return role id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the role identifier.
     *
     * @param id the identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the role name.
     *
     * @return role name
     */
    public String getName() {
        return name;
    }
}
