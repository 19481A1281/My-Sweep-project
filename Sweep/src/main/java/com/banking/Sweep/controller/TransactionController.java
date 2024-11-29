package com.banking.Sweep.controller;


import com.banking.Sweep.DTO.DateRangeDTO;
import com.banking.Sweep.DTO.TransactionDTO;
import com.banking.Sweep.model.Transaction;
import com.banking.Sweep.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bank/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO getTransactionById(@PathVariable Long transactionId){
        return transactionService.getTransactionById(transactionId);
    }


    @GetMapping("/custom-date")
    public List<TransactionDTO> getTransactionInRange(@RequestBody DateRangeDTO dateRangeDTO){
        return transactionService.getTransactionsInRange(dateRangeDTO);
    }

    @GetMapping
    public List<TransactionDTO> getAllTransactions(){
        return transactionService.getAllTransactions();
    }



}
