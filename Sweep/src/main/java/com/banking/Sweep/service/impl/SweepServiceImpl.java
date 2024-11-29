package com.banking.Sweep.service.impl;

import com.banking.Sweep.DTO.AdjustBalanceDTO;
import com.banking.Sweep.Exception.SweepSetupException;
import com.banking.Sweep.controller.AccountController;
import com.banking.Sweep.controller.SweepStatusController;
import com.banking.Sweep.model.Sweep;
import com.banking.Sweep.model.SweepStatus;
import com.banking.Sweep.repository.AccountRepository;
import com.banking.Sweep.repository.SweepRepository;
import com.banking.Sweep.service.SweepService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SweepServiceImpl implements SweepService {
    private final AccountRepository accountRepository;
    private final SweepRepository sweepRepository;
    private final AccountController accountController;
    private final SweepStatusController sweepStatusController;

    private ModelMapper modelMapper;

    public SweepServiceImpl(AccountRepository accountRepository, SweepRepository sweepRepository, AccountController accountController, SweepStatusController sweepStatusController) {
        this.accountRepository = accountRepository;
        this.sweepRepository = sweepRepository;
        this.accountController = accountController;
        this.sweepStatusController = sweepStatusController;
    }


    @Override
    public void addSweep(Sweep sweep) {
        boolean sourceOptedForSweep=accountRepository.findById(sweep.getSourceAccount()).get().isOptForSweep();
        boolean destinationOptedForSweep=accountRepository.findById(sweep.getDestinationAccount()).get().isOptForSweep();

        if(sourceOptedForSweep && destinationOptedForSweep){
            sweepRepository.save(sweep);
        }
        else{
            throw new SweepSetupException("Either source or destination account or both does not opted for sweep");
        }
    }

    //@Transactional
    @Override
    public void startSweep() {

        List<Sweep> sweeps=sweepRepository.findAll();
        sweeps.forEach(sweep -> {
                Long sweepId = sweep.getSweepId(); // Get the sweepId
                Long sourceAccountNumber = sweep.getSourceAccount();
                Long destinationAccountNumber = sweep.getDestinationAccount();

                // Perform your logic here
                Double amountToTransfer = accountRepository.findById(sourceAccountNumber).get().getBalance();

                AdjustBalanceDTO adjustBalanceDTO_1 = new AdjustBalanceDTO(sourceAccountNumber, -amountToTransfer);
                AdjustBalanceDTO adjustBalanceDTO_2 = new AdjustBalanceDTO(destinationAccountNumber, amountToTransfer);

                try {
                    accountController.updateAccountBalance(adjustBalanceDTO_1);
                    accountController.updateAccountBalance(adjustBalanceDTO_2);

                    // Create success status
                    SweepStatus status=new SweepStatus(sweepId, "Success", null, LocalDateTime.now());
                    sweepStatusController.createSweepStatus(status);
                } catch (Exception ex) {
                    // Create failure status with reason
                    SweepStatus status=new SweepStatus(sweepId, "Failed", ex.getMessage(), LocalDateTime.now());
                    sweepStatusController.createSweepStatus(status);
                }
        });
    }
}

