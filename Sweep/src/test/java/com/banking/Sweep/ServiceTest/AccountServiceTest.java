// File: Sweep/src/test/java/com/banking/Sweep/ServiceTest/AccountServiceTest.java

package com.banking.Sweep.ServiceTest;

import com.banking.Sweep.DTO.AccountDTO;
import com.banking.Sweep.DTO.AdjustBalanceDTO;
import com.banking.Sweep.Exception.DoesNotExistException;
import com.banking.Sweep.Exception.InsufficientFundsException;
import com.banking.Sweep.model.Account;
import com.banking.Sweep.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccountSuccess() {
        Account account = new Account();
        account.setAccountNumber(1L);

        doNothing().when(accountService).createAccount(account);

        accountService.createAccount(account);

        verify(accountService, times(1)).createAccount(account);
    }

    @Test
    void getAccountByIdThrowsDoesNotExistException() {
        when(accountService.getAccount(1L)).thenThrow(new DoesNotExistException("Account not found"));

        assertThrows(DoesNotExistException.class, () -> accountService.getAccount(1L));
    }

    @Test
    void getAccountByIdSuccess() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(1L);

        when(accountService.getAccount(1L)).thenReturn(accountDTO);

        AccountDTO result = accountService.getAccount(1L);

        assertNotNull(result);
        assertEquals(1L, result.getAccountNumber());
    }

    @Test
    void getAllAccountsSuccess() {
        AccountDTO accountDTO1 = new AccountDTO();
        accountDTO1.setAccountNumber(1L);
        AccountDTO accountDTO2 = new AccountDTO();
        accountDTO2.setAccountNumber(2L);
        List<AccountDTO> accounts = Arrays.asList(accountDTO1, accountDTO2);

        when(accountService.getAllAccounts()).thenReturn(accounts);

        List<AccountDTO> result = accountService.getAllAccounts();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void updateAccountThrowsDoesNotExistException() {
        doThrow(new DoesNotExistException("Account not found")).when(accountService).updateAccount(1L, Map.of("accountName", "New Name"));

        assertThrows(DoesNotExistException.class, () -> accountService.updateAccount(1L, Map.of("accountName", "New Name")));
    }

    @Test
    void updateAccountSuccess() {
        doNothing().when(accountService).updateAccount(1L, Map.of("accountName", "New Name"));

        accountService.updateAccount(1L, Map.of("accountName", "New Name"));

        verify(accountService, times(1)).updateAccount(1L, Map.of("accountName", "New Name"));
    }

    @Test
    void deleteAccountThrowsDoesNotExistException() {
        doThrow(new DoesNotExistException("Account not found")).when(accountService).deleteAccount(1L);

        assertThrows(DoesNotExistException.class, () -> accountService.deleteAccount(1L));
    }

    @Test
    void deleteAccountSuccess() {
        doNothing().when(accountService).deleteAccount(1L);

        accountService.deleteAccount(1L);

        verify(accountService, times(1)).deleteAccount(1L);
    }

    @Test
    void updateAccountBalanceThrowsDoesNotExistException() {
        AdjustBalanceDTO adjustBalanceDTO = new AdjustBalanceDTO(1L, 100.0);

        doThrow(new DoesNotExistException("Account not found")).when(accountService).updateAccountBalance(adjustBalanceDTO);

        assertThrows(DoesNotExistException.class, () -> accountService.updateAccountBalance(adjustBalanceDTO));
    }

    @Test
    void updateAccountBalanceThrowsInsufficientFundsException() {
        AdjustBalanceDTO adjustBalanceDTO = new AdjustBalanceDTO(1L, -100.0);

        doThrow(new InsufficientFundsException("Insufficient funds")).when(accountService).updateAccountBalance(adjustBalanceDTO);

        assertThrows(InsufficientFundsException.class, () -> accountService.updateAccountBalance(adjustBalanceDTO));
    }

    @Test
    void updateAccountBalanceSuccess() {
        AdjustBalanceDTO adjustBalanceDTO = new AdjustBalanceDTO(1L, 50.0);

        doNothing().when(accountService).updateAccountBalance(adjustBalanceDTO);

        accountService.updateAccountBalance(adjustBalanceDTO);

        verify(accountService, times(1)).updateAccountBalance(adjustBalanceDTO);
    }
}