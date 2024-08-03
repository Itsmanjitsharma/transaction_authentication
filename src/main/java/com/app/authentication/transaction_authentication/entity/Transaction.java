package com.app.authentication.transaction_authentication.entity;

import java.sql.Timestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    @Id
    private int transactionId;
    private long cardNumber;
    private double amount;
    private Timestamp timestamp;
    private int merchantId;
    private String status;

    @PrePersist
    public void prePersist() {
        if (timestamp == null) {
            timestamp = new Timestamp(System.currentTimeMillis());
        }
    }
}
