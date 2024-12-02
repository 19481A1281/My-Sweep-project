package com.banking.Sweep.service;

import com.banking.Sweep.DTO.AccountDTO;
import com.banking.Sweep.DTO.AdjustBalanceDTO;
import com.banking.Sweep.model.Account;

import java.util.List;
import java.util.Map;


public interface AccountService {
    public void createAccount(Account account);

    AccountDTO getAccount(Long accountNumber);

    void updateAccount(Long accountNumber,Map<String,String> updates);

    List<AccountDTO> getAllAccounts();



    void deleteAccount(Long accountNumber);

    void updateAccountBalance(AdjustBalanceDTO adjustBalanceDTO);

    List<AccountDTO> getAllAccountsByUserId(Long userId);
}
