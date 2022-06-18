package com.example.springboot.sender;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "sender")
public class Sender {
    @Id
    @SequenceGenerator(
        name = "sender_sequence",
        sequenceName = "sender_sequence",
        allocationSize = 1,
        initialValue = 1
        
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sender_sequence"
    )
    private long id;

    @Column(name = "company")
    private String company;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;
    
    public Sender(long id, String company, String email, String password, boolean active) {
        this.id = id;
        this.company = company;
        this.email = email;
        this.password = password;
        this.active = active;
    }


    public Sender() {
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    
    
  
    // @OneToMany(mappedBy = "sender")
    // private List<Field> fields;
    
    // @OneToMany(mappedBy = "sender")
    // private List<Transaction> transactions;
   
    
    
}
