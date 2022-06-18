package com.example.springboot.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sender_role")
public class Role {

    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "role")
    private String role;

    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }

    
    public Role() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    

}
