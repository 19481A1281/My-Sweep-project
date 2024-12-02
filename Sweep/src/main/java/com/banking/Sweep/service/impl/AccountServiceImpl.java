package com.banking.Sweep.service.impl;

import com.banking.Sweep.DTO.AccountDTO;
import com.banking.Sweep.DTO.AdjustBalanceDTO;
import com.banking.Sweep.Exception.DoesNotExistException;
import com.banking.Sweep.Exception.InsufficientFundsException;
import com.banking.Sweep.model.Account;
import com.banking.Sweep.model.AccountType;
import com.banking.Sweep.model.Transaction;
import com.banking.Sweep.model.User;
import com.banking.Sweep.repository.AccountRepository;
import com.banking.Sweep.repository.TransactionRepository;
import com.banking.Sweep.repository.UserRepository;
import com.banking.Sweep.service.AccountService;
import com.banking.Sweep.service.TransactionService;
import com.banking.Sweep.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private ModelMapper modelMapper;

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final UserService userService;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionService transactionService, UserService userService) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @Override
    public void createAccount(Account account) {

        accountRepository.save(account);
        Transaction transaction=new Transaction(account.getBalance(), account,LocalDateTime.now());
        transactionService.createTransaction(transaction);
    }

    @Override
    public AccountDTO getAccount(Long accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Use ModelMapper to convert Account entity to AccountDTO
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public void updateAccount(Long accountNumber,Map<String,String> updates) {
        Account account=accountRepository.findById(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        if(updates.containsKey("optForSweep")){
            account.setOptForSweep(Boolean.parseBoolean(updates.get("optForSweep")));
        }
        if(updates.containsKey("accountType")){
            account.setAccountType(AccountType.valueOf(updates.get("accountType")));
        }
//        if (updates.containsKey("user")) {
//            Map<String, Object> userUpdates = new HashMap<>();
//            userUpdates.put("userId",updates.get("userId") );
//            Long newUserId = Long.parseLong(userUpdates.get("userId").toString());
//            User newUser = userService.findById(newUserId);
//            account.setUser(newUser);
//            }
        accountRepository.save(account);
    }

    @Override
    public void updateAccountBalance(AdjustBalanceDTO adjustBalanceDTO) {
        Account account=accountRepository.findById(adjustBalanceDTO.accountNumber()).orElseThrow(()-> new DoesNotExistException("Account doesn't exist"));

        Double newBalance=account.getBalance()+adjustBalanceDTO.amount();

        if(account.getBalance()>0) {
            account.setBalance(newBalance);
            accountRepository.save(account);
            Transaction transaction=new Transaction(adjustBalanceDTO.amount(),account, LocalDateTime.now());
            transactionService.createTransaction(transaction);
        }
        else if(account.getBalance()==0 && newBalance>0){
            account.setBalance(newBalance);
            accountRepository.save(account);
            Transaction transaction=new Transaction(adjustBalanceDTO.amount(),account, LocalDateTime.now());
            transactionService.createTransaction(transaction);
        }
        else {
            throw new InsufficientFundsException("Insufficient funds");
        }

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
