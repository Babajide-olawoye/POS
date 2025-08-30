//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.AAA.demo.entities;


import jakarta.persistence.*;

@Entity
@Table(
        name = "roles"
)
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;



    @Column(
            nullable = false,
            unique = true
    )
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            String trimmed = name.trim();
            if (trimmed.equalsIgnoreCase("Admin")) {
                this.name = "Admin";
                return;
            }
            if (trimmed.equalsIgnoreCase("Technician")) {
                this.name = "Technician";
                return;
            }
            if (trimmed.equalsIgnoreCase("User")) {
                this.name = "User";
                return;
            }
        }
        this.name = "Invalid";
    }


}
