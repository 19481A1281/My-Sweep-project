// File: Sweep/src/test/java/com/banking/Sweep/RepositoryTest/AccountRepositoryTest.java

package com.banking.Sweep.RepositoryTest;

import com.banking.Sweep.model.Account;
import com.banking.Sweep.model.AccountType;
import com.banking.Sweep.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testSaveAccount() {
        Account account = new Account();
        account.setAccountNumber(Long.valueOf("123456789"));
        account.setAccountType(AccountType.valueOf("Savings"));

        Account savedAccount = accountRepository.save(account);

        assertNotNull(savedAccount);
        assertEquals("123456789", savedAccount.getAccountNumber());
    }

    @Test
    void testFindById() {
        Account account = new Account();
        account.setAccountNumber(Long.valueOf("123456789"));
        account.setAccountType(AccountType.valueOf("Savings"));

        Account savedAccount = accountRepository.save(account);
        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getAccountNumber());

        assertTrue(foundAccount.isPresent());
        assertEquals("123456789", foundAccount.get().getAccountNumber());
    }

    @Test
    void testDeleteAccount() {
        Account account = new Account();
        account.setAccountNumber(Long.valueOf("123456789"));
        account.setAccountType(AccountType.valueOf("Savings"));

        Account savedAccount = accountRepository.save(account);
        accountRepository.deleteById(savedAccount.getAccountNumber());

        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getAccountNumber());

        assertFalse(foundAccount.isPresent());
    }
}