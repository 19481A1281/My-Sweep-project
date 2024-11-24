package com.banking.Sweep.DTO;

import com.banking.Sweep.model.AccountType;
import com.banking.Sweep.model.User;

public class AccountDTO{
    private Long accountNumber;
    private boolean optForSweep;
    private Double balance;
    private AccountType accountType;
    private UserDTO user ;

    public AccountDTO() {
    }

    public AccountDTO(Long accountNumber, boolean optForSweep, Double balance, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.optForSweep = optForSweep;
        this.balance = balance;
        this.accountType = accountType;
    }

    public AccountDTO(Long accountNumber, boolean optForSweep, Double balance, AccountType accountType, UserDTO user) {
        this.accountNumber = accountNumber;
        this.optForSweep = optForSweep;
        this.balance = balance;
        this.accountType = accountType;
        this.user = user;
    }


    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isOptForSweep() {
        return optForSweep;
    }

    public void setOptForSweep(boolean optForSweep) {
        this.optForSweep = optForSweep;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
