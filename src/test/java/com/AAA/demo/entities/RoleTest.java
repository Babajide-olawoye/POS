package com.AAA.demo.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    @Test
    void setNameAcceptsValidRoles() {
        Role role = new Role();
        assertDoesNotThrow(() -> role.setName("Admin"));
        assertEquals("Admin", role.getName());
    }

    @Test
    void setNameCanonicalizesCase() {
        Role role = new Role();
        role.setName("uSeR");
        assertEquals("User", role.getName());
    }

    @Test
    void setNameTrimsWhitespace() {
        Role role = new Role();
        role.setName("  Technician  ");
        assertEquals("Technician", role.getName());
    }

    @Test
    void setNameWithNullDefaultsToInvalid() {
        Role role = new Role();
        assertDoesNotThrow(() -> role.setName(null));
        assertEquals("Invalid", role.getName());
    }
}
