// File: Sweep/src/test/java/com/banking/Sweep/ServiceTest/UserServiceTest.java

package com.banking.Sweep.ServiceTest;

import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.Exception.DoesNotExistException;
import com.banking.Sweep.Exception.DuplicateEmailException;
import com.banking.Sweep.Exception.PasswordPatternMissMatchException;
import com.banking.Sweep.model.User;
import com.banking.Sweep.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserThrowsDuplicateEmailException() {
        User user = new User();
        user.setUserEmail("test@example.com");

        doThrow(new DuplicateEmailException("Email already exists")).when(userService).createUser(user);

        assertThrows(DuplicateEmailException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserThrowsPasswordPatternMissMatchException() {
        User user = new User();
        user.setUserEmail("test@example.com");
        user.setPassword("weakpassword");

        doThrow(new PasswordPatternMissMatchException("Password does not match pattern")).when(userService).createUser(user);

        assertThrows(PasswordPatternMissMatchException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserSuccess() {
        User user = new User();
        user.setUserEmail("test@example.com");
        user.setPassword("StrongPass1!");

        doNothing().when(userService).createUser(user);

        userService.createUser(user);

        verify(userService, times(1)).createUser(user);
    }

    @Test
    void retriveUserByIdThrowsDoesNotExistException() {
        doThrow(new DoesNotExistException("User not found")).when(userService).retriveUserById(1L);

        assertThrows(DoesNotExistException.class, () -> userService.retriveUserById(1L));
    }

    @Test
    void retriveUserByIdSuccess() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);

        when(userService.retriveUserById(1L)).thenReturn(userDTO);

        UserDTO result = userService.retriveUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
    }

    @Test
    void getAllUsersSuccess() {
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setUserId(1L);
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setUserId(2L);
        List<UserDTO> users = Arrays.asList(userDTO1, userDTO2);

        when(userService.getAllUsers()).thenReturn(users);

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void updateUserThrowsDoesNotExistException() {
        doThrow(new DoesNotExistException("User not found")).when(userService).updateUser(1L, Map.of("userName", "New Name"));

        assertThrows(DoesNotExistException.class, () -> userService.updateUser(1L, Map.of("userName", "New Name")));
    }

    @Test
    void updateUserThrowsDuplicateEmailException() {
        doThrow(new DuplicateEmailException("Email already exists")).when(userService).updateUser(1L, Map.of("userEmail", "new@example.com"));

        assertThrows(DuplicateEmailException.class, () -> userService.updateUser(1L, Map.of("userEmail", "new@example.com")));
    }

    @Test
    void updateUserSuccess() {
        doNothing().when(userService).updateUser(1L, Map.of("userEmail", "new@example.com"));

        userService.updateUser(1L, Map.of("userEmail", "new@example.com"));

        verify(userService, times(1)).updateUser(1L, Map.of("userEmail", "new@example.com"));
    }

    @Test
    void deleteUserThrowsDoesNotExistException() {
        doThrow(new DoesNotExistException("User not found")).when(userService).deleteUser(1L);

        assertThrows(DoesNotExistException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void deleteUserSuccess() {
        doNothing().when(userService).deleteUser(1L);

        userService.deleteUser(1L);

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void getUserByEmailThrowsDoesNotExistException() {
        doThrow(new DoesNotExistException("User not found")).when(userService).getUserByEmail("test@example.com");

        assertThrows(DoesNotExistException.class, () -> userService.getUserByEmail("test@example.com"));
    }

    @Test
    void getUserByEmailSuccess() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserEmail("test@example.com");

        when(userService.getUserByEmail("test@example.com")).thenReturn(userDTO);

        UserDTO result = userService.getUserByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getUserEmail());
    }
}