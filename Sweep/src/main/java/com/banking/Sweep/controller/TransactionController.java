package com.banking.Sweep.controller;

import com.banking.Sweep.model.Transaction;
import com.banking.Sweep.service.TransactionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("bank/balance")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


}
