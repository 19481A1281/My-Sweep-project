package com.banking.Sweep.model;

import jakarta.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId; //only integer values

    @Column(nullable = false) //columnDefinition = "DECIMAL(10,2) DEFAULT 0.0"
    private Double amount=0.0; //can store fraction values

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false) // Link to Account
    private Account account;


    //account owns the relation

    //mappedBy = "transaction"
    /*Purpose: Indicates that this relationship is mapped by the transaction field in the Account entity.
     Details: The mappedBy attribute tells JPA that the Transaction entity does not own the relationship.
     Instead, the Account entity owns the relationship via the transaction field.
     */

    public Transaction() {

    }

    public Transaction(Long transactionId, Double amount, Account account) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.account = account;
    }

    public Transaction(Double amount, Account account) {
        this.amount=amount;
        this.account=account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
