package com.banking.Sweep.ControllerTest;

import com.banking.Sweep.DTO.DateRangeDTO;
import com.banking.Sweep.DTO.TransactionDTO;
import com.banking.Sweep.controller.TransactionController;
import com.banking.Sweep.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void getTransactionByIdReturnsTransaction() throws Exception {

        TransactionDTO transactionDTO = new TransactionDTO(1L, 100.0, 1L);
        when(transactionService.getTransactionById(1L)).thenReturn(transactionDTO);

        mockMvc.perform(get("/bank/transaction/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"transactionId\":1,\"amount\":100.0,\"accountNumber\":1}"));
    }

    @Test
    void getTransactionInRangeReturnsListOfTransactions() throws Exception {
        DateRangeDTO dateRangeDTO = new DateRangeDTO("2023-01-01T00:00:00", "2023-12-31T00:00:00");
        List<TransactionDTO> transactions = Arrays.asList(
                new TransactionDTO(1L, 100.0, 1L),
                new TransactionDTO(2L, 200.0, 1L)
        );
        when(transactionService.getTransactionsInRange(any(DateRangeDTO.class))).thenReturn(transactions);

        mockMvc.perform(get("/bank/transaction/custom-date")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2023-01-01T00:00:00\",\"endDate\":\"2023-12-31T00:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"transactionId\":1,\"amount\":100.0,\"accountNumber\":1},{\"transactionId\":2,\"amount\":200.0,\"accountNumber\":1}]"));
    }

    @Test
    void getAllTransactionsReturnsListOfTransactions() throws Exception {
        List<TransactionDTO> transactions = Arrays.asList(
                new TransactionDTO(1L, 100.0, 1L),
                new TransactionDTO(2L, 200.0, 1L)
        );
        when(transactionService.getAllTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/bank/transaction")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"transactionId\":1,\"amount\":100.0,\"accountNumber\":1},{\"transactionId\":2,\"amount\":200.0,\"accountNumber\":1}]"));
    }
}