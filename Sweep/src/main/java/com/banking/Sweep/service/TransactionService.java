package com.banking.Sweep.service;

import com.banking.Sweep.DTO.DateRangeDTO;
import com.banking.Sweep.DTO.TransactionDTO;
import com.banking.Sweep.model.Transaction;

import java.util.List;

public interface TransactionService {


    TransactionDTO getTransactionById(Long transactionId);

    List<TransactionDTO> getAllTransactions();

    List<TransactionDTO> getTransactionsInRange(DateRangeDTO dateRangeDTO);
}
