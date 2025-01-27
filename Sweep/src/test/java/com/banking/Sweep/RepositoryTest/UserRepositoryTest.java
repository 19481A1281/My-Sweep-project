// File: Sweep/src/test/java/com/banking/Sweep/RepositoryTest/UserRepositoryTest.java

package com.banking.Sweep.RepositoryTest;

import com.banking.Sweep.model.User;
import com.banking.Sweep.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUserEmail("test@example.com");
        user.setPassword("StrongPass1!");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getUserEmail());
    }

    @Test
    void testFindById() {
        User user = new User();
        user.setUserEmail("test@example.com");
        user.setPassword("StrongPass1!");

        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getUserId());

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getUserEmail());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUserEmail("test@example.com");
        user.setPassword("StrongPass1!");

        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getUserId());

        Optional<User> foundUser = userRepository.findById(savedUser.getUserId());

        assertFalse(foundUser.isPresent());
    }
}