package com.banking.Sweep.service.impl;

import com.banking.Sweep.DTO.AccountDTO;
import com.banking.Sweep.DTO.AdjustBalanceDTO;
import com.banking.Sweep.model.Account;
import com.banking.Sweep.model.Transaction;
import com.banking.Sweep.repository.AccountRepository;
import com.banking.Sweep.repository.TransactionRepository;
import com.banking.Sweep.repository.UserRepository;
import com.banking.Sweep.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private ModelMapper modelMapper;

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void createAccount(Account account) {

        accountRepository.save(account);
        Transaction transaction=new Transaction(account.getBalance(), account);
        transactionRepository.save(transaction);
    }

    @Override
    public AccountDTO getAccount(Long accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Use ModelMapper to convert Account entity to AccountDTO
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void updateAccountBalance(AdjustBalanceDTO adjustBalanceDTO) {
        Account account=accountRepository.findById(adjustBalanceDTO.accountNumber()).get();

        Double newBalance=account.getBalance()+adjustBalanceDTO.amount();

        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction=new Transaction(newBalance,account);
        transactionRepository.save(transaction);
    }

    @Override
    public List<AccountDTO> getAllAccountsByUserId(Long userId) {

        List<Account> accountList=accountRepository.findAllAccountsByUserId(userId);
        return accountList.stream().map(account -> modelMapper.map(account,AccountDTO.class)).collect(Collectors.toList());
        //return accountRepository.findAllAccountsByUserId(userId).get();
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accountsList = accountRepository.findAll();
        return accountsList.stream().map(account -> modelMapper.map(account, AccountDTO.class)).collect(Collectors.toList());
    }


    @Override
    public void deleteAccount(Long accountNumber) {
        accountRepository.deleteById(accountNumber);
    }
}
