package com.banking.Sweep.ControllerTest;

import com.banking.Sweep.DTO.AccountDTO;
import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.controller.AccountController;
import com.banking.Sweep.model.Account;
import com.banking.Sweep.model.AccountType;
import com.banking.Sweep.model.User;
import com.banking.Sweep.model.UserType;
import com.banking.Sweep.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void createAccountReturnsSuccessMessage() throws Exception {
        User user = new User(1L, "John Doe", "john.doe@example.com", "password", UserType.CUSTOMER);
        Account account = new Account(1L, true, 1000.0, user, AccountType.SAVINGS);
        doNothing().when(accountService).createAccount(any(Account.class));

        mockMvc.perform(post("/bank/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountNumber\":1,\"optForSweep\":true,\"balance\":1000.0,\"user\":{\"userId\":1},\"accountType\":\"SAVINGS\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account Created Successfully"));
    }

    @Test
    void getAccountByIdReturnsAccount() throws Exception {
        UserDTO user=new UserDTO(1L,"John Doe","john.doe@example.com",UserType.CUSTOMER.toString());
        AccountDTO accountDTO = new AccountDTO(1L, true, 1000.0, AccountType.SAVINGS, user);
        when(accountService.getAccount(1L)).thenReturn(accountDTO);

        mockMvc.perform(get("/bank/account/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"accountNumber\":1,\"optForSweep\":true,\"balance\":1000.0,\"accountType\":\"SAVINGS\",\"user\":{\"userId\":1,\"userName\":\"John Doe\",\"userEmail\":\"john.doe@example.com\",\"userType\":\"CUSTOMER\"}}"));
    }

    @Test
    void getAllAccountsReturnsListOfAccounts() throws Exception {
        UserDTO user=new UserDTO(1L,"John Doe","john.doe@example.com",UserType.CUSTOMER.toString());
        List<AccountDTO> accounts = Arrays.asList(
                new AccountDTO(1L, true, 1000.0, AccountType.SAVINGS, user),
                new AccountDTO(2L, false, 2000.0, AccountType.CURRENT, user)
        );
        when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/bank/account")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"accountNumber\":1,\"optForSweep\":true,\"balance\":1000.0,\"accountType\":\"SAVINGS\",\"user\":{\"userId\":1,\"userName\":\"John Doe\",\"userEmail\":\"john.doe@example.com\",\"userType\":\"CUSTOMER\"}},{\"accountNumber\":2,\"optForSweep\":false,\"balance\":2000.0,\"accountType\":\"CURRENT\",\"user\":{\"userId\":1,\"userName\":\"John Doe\",\"userEmail\":\"john.doe@example.com\",\"userType\":\"CUSTOMER\"}}]"));
    }

    @Test
    void getAllAccountsByUserIdReturnsListOfAccounts() throws Exception {
        UserDTO user=new UserDTO(1L,"John Doe","john.doe@example.com",UserType.CUSTOMER.toString());
        List<AccountDTO> accounts = Arrays.asList(new AccountDTO(1L, true, 1000.0, AccountType.SAVINGS, user));
        when(accountService.getAllAccountsByUserId(1L)).thenReturn(accounts);

        mockMvc.perform(get("/bank/account/userId/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"accountNumber\":1,\"optForSweep\":true,\"balance\":1000.0,\"accountType\":\"SAVINGS\",\"user\":{\"userId\":1,\"userName\":\"John Doe\",\"userEmail\":\"john.doe@example.com\",\"userType\":\"CUSTOMER\"}}]"));
    }

    @Test
    void updateAccountReturnsSuccessMessage() throws Exception {
        Map<String, String> updates = Map.of("optForSweep", "false");
        doNothing().when(accountService).updateAccount(1L, updates);

        mockMvc.perform(patch("/bank/account/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"optForSweep\":\"false\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account updated successfully"));
    }

    @Test
    void deleteAccountReturnsSuccessMessage() throws Exception {
        doNothing().when(accountService).deleteAccount(1L);

        mockMvc.perform(delete("/bank/account/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Account deleted successfully"));
    }
}