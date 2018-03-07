package com.dicky.authserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role", schema = "octo_test")
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;

    @Column(name = "role_name", nullable = false)
    private String role_name;

    @Column(name = "description", nullable = false)
    private String description;

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
