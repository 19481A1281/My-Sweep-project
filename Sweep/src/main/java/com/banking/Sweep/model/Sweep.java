package com.banking.Sweep.model;

import jakarta.persistence.*;

@Entity
public class Sweep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sweepId;

    @ManyToOne
    @JoinColumn(name = "sourceAccountNumber",referencedColumnName = "accountNumber",nullable = false,unique = true)
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destinationAccountNumber",referencedColumnName = "accountNumber",nullable = false,unique = true)
    private Account destinationAccount;

    public Sweep() {
    }



    public Sweep(Long sweepId, Account sourceAccount, Account destinationAccount) {
        this.sweepId = sweepId;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }

    public Long getSweepId() {
        return sweepId;
    }

    public void setSweepId(Long sweepId) {
        this.sweepId = sweepId;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
