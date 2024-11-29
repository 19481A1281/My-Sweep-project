package com.banking.Sweep.DTO;

import com.banking.Sweep.model.Account;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long transactionId; //only integer values
    private Double amount; //can store fraction values
    private Long accountNumber;
    private LocalDateTime timeStamp;


    public TransactionDTO() {
    }

    public TransactionDTO(Long transactionId, Double amount, Long accountNumber, LocalDateTime timeStamp) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.timeStamp = timeStamp;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
