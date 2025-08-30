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
     * Name of the role. Must be unique and one of Admin, Technician or User.
     */
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
