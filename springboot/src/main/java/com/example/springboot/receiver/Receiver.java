package com.example.springboot.receiver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
// import org.json.simple.JSONObject;

@Entity
@Table
public class Receiver { 
    @Id
    @SequenceGenerator(
        name = "receiver_sequence",
        sequenceName = "receiver_sequence",
        allocationSize = 1,
        initialValue = 1
        
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "receiver_sequence"
    )
    private long receiverID;
    private String receiverName;
    private long countryID;
    private String apiLink;

    public Receiver() {
    }

    public Receiver(long receiverID, String receiverName, long countryID, String apiLink) {
        this.receiverID = receiverID;
        this.receiverName = receiverName;
        this.countryID = countryID; 
        this.apiLink = apiLink;
    }

    public long getReceiverID() {
        return receiverID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public long getCountryID() {
        return countryID;
    }

    public String getApiLink() {
        return apiLink;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setApiLink(String apiLink) {
        this.apiLink = apiLink;
    }

     
}