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

    public void setName(String name) {

        if(name.equalsIgnoreCase("Admin") || name.equalsIgnoreCase("Technician") || name.equalsIgnoreCase("User"))
        this.name = name;
        else
            this.name = "Invalid";
    }


}
