package com.banking.Sweep.controller;

import com.banking.Sweep.DTO.AccountDTO;
import com.banking.Sweep.DTO.AdjustBalanceDTO;
import com.banking.Sweep.model.Account;
import com.banking.Sweep.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public String registerAccount(@RequestBody Account account){
        accountService.createAccount(account);
        return "Account Created Successfully";
    }

    @GetMapping("/{accountNumber}")
    public AccountDTO getAccountByAccountNumber(@PathVariable Long accountNumber){
        return accountService.getAccount(accountNumber);

    }

    @GetMapping("/userId/{userId}")
    public List<AccountDTO> getAllAccountsByUserId(@PathVariable Long userId){
        return accountService.getAllAccountsByUserId(userId);

    }

    @GetMapping
    public List<AccountDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }


    @PutMapping()
    public String updateAccount(@RequestBody Account account){
        accountService.updateAccount(account);
        return "Account updated successfully";
    }

    @PatchMapping("adjust-balance")
    public String updateAccountBalance(@RequestBody AdjustBalanceDTO adjustBalanceDTO){
        accountService.updateAccountBalance(adjustBalanceDTO);
        return "Account Updated successfully";
    }


    @DeleteMapping("{accountNumber}")
    public String deleteAccount(@PathVariable Long accountNumber){
        accountService.deleteAccount(accountNumber);
        return "Account deleted successfully";
    }

}
