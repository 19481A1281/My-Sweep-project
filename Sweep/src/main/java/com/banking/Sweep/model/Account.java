package com.banking.Sweep.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @Column(nullable = false)
    private boolean optForSweep;

    @Column(nullable = false)
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name="userId",nullable = false)
    private User user;//Account owns the relation

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;




    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
        this.optForSweep = false;// Default value is false
        this.balance=0.0;
    }

    public Account(Long accountNumber, boolean optForSweep, Double balance, User user, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.optForSweep = optForSweep;
        this.balance = balance;
        this.user = user;
        this.accountType = accountType;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    //@JsonProperty("optForSweep")
    public boolean isOptForSweep() {
        return optForSweep;
    }

    public void setOptForSweep(boolean optForSweep) {
        this.optForSweep = optForSweep;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}


