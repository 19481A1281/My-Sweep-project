package com.banking.Sweep.service.impl;

import com.banking.Sweep.DTO.AdjustBalanceDTO;
import com.banking.Sweep.controller.AccountController;
import com.banking.Sweep.model.Sweep;
import com.banking.Sweep.repository.AccountRepository;
import com.banking.Sweep.repository.SweepRepository;
import com.banking.Sweep.service.AccountService;
import com.banking.Sweep.service.SweepService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SweepServiceImpl implements SweepService {
    private final AccountRepository accountRepository;
    private final SweepRepository sweepRepository;
    private final AccountController accountController;

    public SweepServiceImpl(AccountRepository accountRepository, SweepRepository sweepRepository, AccountController accountController) {
        this.accountRepository = accountRepository;
        this.sweepRepository = sweepRepository;
        this.accountController = accountController;

    }


    //@Transactional
    @Override
    public void startSweep() {
        List<Sweep> accountList=sweepRepository.findAll();
        for(Sweep sweep : accountList){

            Long sourceAccountNumber=sweep.getSourceAccount().getAccountNumber();
            Long destinationAccount=sweep.getDestinationAccount().getAccountNumber();

            Double amountToTransfer=sweep.getSourceAccount().getBalance();

            AdjustBalanceDTO adjustBalanceDTO_1=new AdjustBalanceDTO(sourceAccountNumber,-amountToTransfer);
            AdjustBalanceDTO adjustBalanceDTO_2=new AdjustBalanceDTO(destinationAccount,amountToTransfer);

            accountController.updateAccountBalance(adjustBalanceDTO_1);
            accountController.updateAccountBalance(adjustBalanceDTO_2);
            
        }
    }
}
