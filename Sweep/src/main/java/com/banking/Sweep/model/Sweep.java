package com.banking.Sweep.model;

import jakarta.persistence.*;

@Entity
public class Sweep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sweepId;

//    @ManyToOne
//    @JoinColumn(name = "sourceAccountNumber",referencedColumnName = "accountNumber",nullable = false,unique = true)
    @Column(nullable = false)
    private Long sourceAccount;

//    @ManyToOne
//    @JoinColumn(name = "destinationAccountNumber",referencedColumnName = "accountNumber",nullable = false,unique = true)
    @Column(nullable = false)
    private Long destinationAccount;

    public Sweep() {
    }

    public Sweep(Long sweepId, Long sourceAccount, Long destinationAccount) {
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

    public Long getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Long sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Long getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Long destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
