package com.banking.Sweep.RepositoryTest;

import com.banking.Sweep.model.Account;
import com.banking.Sweep.model.AccountType;
import com.banking.Sweep.model.Transaction;
import com.banking.Sweep.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class TransactionRepositoryTest {

    @Mock
    private TransactionRepository transactionRepository;


    private Account account;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountNumber(1L);
        account.setAccountType(AccountType.SAVINGS);

        transaction = new Transaction(1L,1000.0, account, LocalDateTime.now());
    }

    @Test
    void testSaveTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction savedTransaction = transactionRepository.save(transaction);

        assertNotNull(savedTransaction);
        assertEquals(1000.0, savedTransaction.getAmount());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testFindById() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        Optional<Transaction> foundTransaction = transactionRepository.findById(transaction.getTransactionId());

        assertTrue(foundTransaction.isPresent());
        assertEquals(1000.0, foundTransaction.get().getAmount());
        verify(transactionRepository, times(1)).findById(transaction.getTransactionId());
    }




}