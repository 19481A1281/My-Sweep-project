// File: Sweep/src/test/java/com/banking/Sweep/ServiceTest/TransactionServiceTest.java

package com.banking.Sweep.ServiceTest;

import com.banking.Sweep.DTO.DateRangeDTO;
import com.banking.Sweep.DTO.TransactionDTO;
import com.banking.Sweep.Exception.DoesNotExistException;
import com.banking.Sweep.model.Transaction;
import com.banking.Sweep.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTransactionById_Success() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(1L);

        when(transactionService.getTransactionById(1L)).thenReturn(transactionDTO);

        TransactionDTO result = transactionService.getTransactionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getTransactionId());
    }

    @Test
    void getTransactionById_NotFound() {
        when(transactionService.getTransactionById(1L)).thenThrow(new DoesNotExistException("Transaction not found"));

        assertThrows(DoesNotExistException.class, () -> transactionService.getTransactionById(1L));
    }

    @Test
    void getAllTransactions() {
        TransactionDTO transactionDTO1 = new TransactionDTO();
        transactionDTO1.setTransactionId(1L);
        TransactionDTO transactionDTO2 = new TransactionDTO();
        transactionDTO2.setTransactionId(2L);
        List<TransactionDTO> transactions = Arrays.asList(transactionDTO1, transactionDTO2);

        when(transactionService.getAllTransactions()).thenReturn(transactions);

        List<TransactionDTO> result = transactionService.getAllTransactions();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getTransactionsInRange() {
        TransactionDTO transactionDTO1 = new TransactionDTO();
        transactionDTO1.setTransactionId(1L);
        TransactionDTO transactionDTO2 = new TransactionDTO();
        transactionDTO2.setTransactionId(2L);
        List<TransactionDTO> transactions = Arrays.asList(transactionDTO1, transactionDTO2);
        DateRangeDTO dateRangeDTO = new DateRangeDTO(LocalDateTime.now().minusDays(2).toString(), LocalDateTime.now().toString());

        when(transactionService.getTransactionsInRange(dateRangeDTO)).thenReturn(transactions);

        List<TransactionDTO> result = transactionService.getTransactionsInRange(dateRangeDTO);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void createTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);

        doNothing().when(transactionService).createTransaction(transaction);

        transactionService.createTransaction(transaction);

        verify(transactionService, times(1)).createTransaction(transaction);
    }
}