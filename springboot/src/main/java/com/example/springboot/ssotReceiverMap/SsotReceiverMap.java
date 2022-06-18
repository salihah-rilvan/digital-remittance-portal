package com.example.springboot.ssotReceiverMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table
public class SsotReceiverMap {
    @Id
    @SequenceGenerator(
        name = "SsotReceiverMap_sequence",
        sequenceName = "SsotReceiverMap_sequence",
        allocationSize = 1,
        initialValue = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "SsotReceiverMap_sequence"
    )
    private long id;
    private long ssotId;
    private long receiverId;
    private String receiverFieldName;
    
    public SsotReceiverMap() {
    }

    public SsotReceiverMap(long id, long ssotId, long receiverId, String receiverFieldName) {
        this.id = id;
        this.ssotId = ssotId;
        this.receiverId = receiverId;
        this.receiverFieldName = receiverFieldName;
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

    public void setSsotId(long id) {
        this.id = ssotId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverFieldName() {
        return receiverFieldName;
    }

    public void setReceiverFieldName(String receiverFieldName) {
        this.receiverFieldName = receiverFieldName;
    }


    
    
}