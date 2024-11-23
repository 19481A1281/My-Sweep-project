package com.banking.Sweep.service;

import com.banking.Sweep.DTO.AccountDTO;
import com.banking.Sweep.DTO.AdjustBalanceDTO;
import com.banking.Sweep.model.Account;

import java.util.List;


public interface AccountService {
    public void createAccount(Account account);

    AccountDTO getAccount(Long accountNumber);

    void updateAccount(Account account);

    List<AccountDTO> getAllAccounts();



    void deleteAccount(Long accountNumber);

    void updateAccountBalance(AdjustBalanceDTO adjustBalanceDTO);

    List<AccountDTO> getAllAccountsByUserId(Long userId);
}
