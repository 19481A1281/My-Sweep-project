package com.banking.Sweep.service.impl;

import com.banking.Sweep.DTO.DateRangeDTO;
import com.banking.Sweep.DTO.TransactionDTO;
import com.banking.Sweep.Exception.DoesNotExistException;
import com.banking.Sweep.model.Transaction;
import com.banking.Sweep.repository.TransactionRepository;
import com.banking.Sweep.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private ModelMapper modelMapper;
    private final TransactionRepository transactionRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public TransactionDTO getTransactionById(Long transactionId) {
        Transaction transaction=transactionRepository.findById(transactionId).orElseThrow(()-> new DoesNotExistException("TransactionId :"+transactionId+"does not exist"));
        return modelMapper.map(transaction,TransactionDTO.class);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions=transactionRepository.findAll();
        return transactions.stream().map(transaction -> modelMapper.map(transaction,TransactionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsInRange(DateRangeDTO dateRangeDTO) {
        List<Transaction> transactions=transactionRepository.findAllByTimeStampBetween(LocalDateTime.parse(dateRangeDTO.getStartDate()), LocalDateTime.parse(dateRangeDTO.getEndDate()));
        return transactions.stream().map(transaction -> modelMapper.map(transaction,TransactionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
