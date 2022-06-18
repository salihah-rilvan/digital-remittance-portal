package com.example.springboot.ssotSenderMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table
public class SsotSenderMap {
    @Id
    @SequenceGenerator(
        name = "SsotSenderMap_sequence",
        sequenceName = "SsotSenderMap_sequence",
        allocationSize = 1,
        initialValue = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "SsotSenderMap_sequence"
    )
    private long id;
    private long ssotId;
    private String field;
    private long userId;

    
    public SsotSenderMap() {
    }

    public SsotSenderMap(long id, long ssotId, String field, long userId) {
        this.id = id;
        this.ssotId = ssotId;
        this.field = field;
        this.userId = userId;
    }

    public SsotSenderMap(long ssotId, String field, long userId) {
        this.ssotId = ssotId;
        this.field = field;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSsotId() {
        return ssotId;
    }

    public void setSsotId(long ssotId) {
        this.ssotId = ssotId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    
    
}