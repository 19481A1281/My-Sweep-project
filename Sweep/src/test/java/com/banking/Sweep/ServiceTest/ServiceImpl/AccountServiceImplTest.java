package com.banking.Sweep.ServiceTest;

import com.banking.Sweep.DTO.AccountDTO;
import com.banking.Sweep.DTO.AdjustBalanceDTO;
import com.banking.Sweep.Exception.DoesNotExistException;
import com.banking.Sweep.Exception.InsufficientFundsException;
import com.banking.Sweep.model.Account;
import com.banking.Sweep.model.AccountType;
import com.banking.Sweep.model.Transaction;
import com.banking.Sweep.repository.AccountRepository;
import com.banking.Sweep.service.TransactionService;
import com.banking.Sweep.service.UserService;
import com.banking.Sweep.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createAccountSuccess() {
        Account account = new Account();
        account.setAccountNumber(1L);
        account.setAccountType(AccountType.SAVINGS);
        account.setBalance(1000.0);

        accountService.createAccount(account);

        verify(accountRepository, times(1)).save(account);
        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
    }

    @Test
    void retrieveAccountByIdThrowsDoesNotExistException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DoesNotExistException.class, () -> accountService.getAccount(1L));
    }

    @Test
    void retrieveAccountByIdSuccess() {
        Account account = new Account();
        account.setAccountNumber(1L);
        AccountDTO accountDTO = new AccountDTO();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(accountDTO);

        AccountDTO result = accountService.getAccount(1L);

        assertEquals(accountDTO, result);
    }

    @Test
    void getAllAccountsSuccess() {
        List<Account> accounts = Arrays.asList(new Account(), new Account());
        List<AccountDTO> accountDTOs = Arrays.asList(new AccountDTO(), new AccountDTO());

        when(accountRepository.findAll()).thenReturn(accounts);
        when(modelMapper.map(any(Account.class), eq(AccountDTO.class))).thenReturn(accountDTOs.get(0), accountDTOs.get(1));

        List<AccountDTO> result = accountService.getAllAccounts();

        assertEquals(accountDTOs, result);
    }

    @Test
    void updateAccountThrowsDoesNotExistException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DoesNotExistException.class, () -> accountService.updateAccount(1L, Map.of("accountName", "New Name")));
    }

    @Test
    void updateAccountSuccess() {
        Account account = new Account();
        account.setAccountNumber(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.updateAccount(1L, Map.of("accountName", "New Name"));

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void deleteAccountThrowsDoesNotExistException() {
        when(accountRepository.existsById(1L)).thenReturn(false);

        assertThrows(DoesNotExistException.class, () -> accountService.deleteAccount(1L));
    }

    @Test
    void deleteAccountSuccess() {
        when(accountRepository.existsById(1L)).thenReturn(true);

        accountService.deleteAccount(1L);

        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateAccountBalanceThrowsDoesNotExistException() {
        AdjustBalanceDTO adjustBalanceDTO = new AdjustBalanceDTO(1L, 100.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DoesNotExistException.class, () -> accountService.updateAccountBalance(adjustBalanceDTO));
    }

    @Test
    void updateAccountBalanceThrowsInsufficientFundsException() {
        Account account = new Account();
        account.setAccountNumber(1L);
        account.setBalance(50.0);
        AdjustBalanceDTO adjustBalanceDTO = new AdjustBalanceDTO(1L, -100.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class, () -> accountService.updateAccountBalance(adjustBalanceDTO));
    }

    @Test
    void updateAccountBalanceSuccess() {
        Account account = new Account();
        account.setAccountNumber(1L);
        account.setBalance(100.0);
        AdjustBalanceDTO adjustBalanceDTO = new AdjustBalanceDTO(1L, 50.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.updateAccountBalance(adjustBalanceDTO);

        verify(accountRepository, times(1)).save(account);
        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
    }
}