package com.banking.Sweep.service.impl;

import com.banking.Sweep.model.Transaction;
import com.banking.Sweep.repository.TransactionRepository;
import com.banking.Sweep.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


}
