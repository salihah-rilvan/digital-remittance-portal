package com.example.springboot.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

import com.vladmihalcea.hibernate.type.json.JsonType;
import com.vladmihalcea.hibernate.type.json.internal.JacksonUtil;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
@Table
@TypeDef(name = "json", typeClass = JsonType.class)
public class Transaction {
    @Id
    @SequenceGenerator(
        name = "transaction_sequence",
        sequenceName = "transaction_sequence",
        allocationSize = 1,
        initialValue = 1
        
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "transaction_sequence"
    )
    private long transactionId;

    @Column(name = "sender_id")
    private long senderId;

    @Column(name = "receiver_id")
    private long receiverId;

    @Column(name = "date")
    private LocalDate dateTime;

    @Column(name = "status")
    private String status;
    
    @Type(type = "json")
    @Column(name = "info", columnDefinition = "jsonb")
    private String argsInfo; 

    

    public Transaction() {
    }

    public Transaction(long transactionId, long senderId, long receiverId, LocalDate dateTime, String status,
        String argsInfo) {
        this.transactionId = transactionId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.dateTime = dateTime;
        this.status = status;
        this.argsInfo = argsInfo;
    }

    public Transaction(long senderId, long receiverId, LocalDate dateTime, String status,
    String argsInfo) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.dateTime = dateTime;
        this.status = status;
        this.argsInfo = argsInfo;
    }



    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JsonNode getJsonNodeArgsInfo() {
        return JacksonUtil.toJsonNode(argsInfo);
    }


    public void setArgsInfo(String argsInfo) {
        this.argsInfo = argsInfo;
    }
    
}
