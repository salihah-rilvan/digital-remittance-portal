package com.example.springboot.ssot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.*;

@Entity
@Table
public class Ssot {
    @Id
    @SequenceGenerator(
        name = "ssot_sequence",
        sequenceName = "ssot_sequence",
        allocationSize = 1,
        initialValue = 1
        
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "ssot_sequence"
    )
    private long ssotId;
    private String ssotName;
    
    
    public Ssot() {
    }

    public Ssot(String ssotName) {
        this.ssotName = ssotName;
    }

    public Ssot(long ssotId, String ssotName) {
        this.ssotId = ssotId;
        this.ssotName = ssotName;
    }

    public long getSsotId() {
        return ssotId;
    }

    public void setSsotId(long ssotId) {
        this.ssotId = ssotId;
    }

    public String getSsotName() {
        return ssotName;
    }

    public void setSsotName(String ssotName) {
        this.ssotName = ssotName;
    }

}
