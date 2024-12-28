package com.banking.Sweep.ControllerTest;



import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.controller.UserController;
import com.banking.Sweep.model.User;
import com.banking.Sweep.model.UserType;
import com.banking.Sweep.service.UserService;
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

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void createUserReturnsSuccessMessage() throws Exception {
        User user = new User(1L, "John Doe", "john.doe@example.com", "password", UserType.CUSTOMER);
        doNothing().when(userService).createUser(any(User.class));

        mockMvc.perform(post("/bank/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"userName\":\"John Doe\",\"userEmail\":\"john.doe@example.com\",\"password\":\"password\",\"userType\":\"CUSTOMER\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void getUserByIdReturnsUser() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "John Doe", "john.doe@example.com", "CUSTOMER");
        when(userService.retriveUserById(1L)).thenReturn(userDTO);

        mockMvc.perform(get("/bank/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"userId\":1,\"userName\":\"John Doe\",\"userEmail\":\"john.doe@example.com\",\"userType\":\"CUSTOMER\"}"));
    }

    @Test
    void getAllUsersReturnsListOfUsers() throws Exception {
        List<UserDTO> users = Arrays.asList(
                new UserDTO(1L, "John Doe", "john.doe@example.com", "CUSTOMER"),
                new UserDTO(2L, "Jane Doe", "jane.doe@example.com", "ADMIN")
        );
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/bank/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"userId\":1,\"userName\":\"John Doe\",\"userEmail\":\"john.doe@example.com\",\"userType\":\"CUSTOMER\"},{\"userId\":2,\"userName\":\"Jane Doe\",\"userEmail\":\"jane.doe@example.com\",\"userType\":\"ADMIN\"}]"));
    }

    @Test
    void updateUserReturnsSuccessMessage() throws Exception {
        Map<String, String> updates = Map.of("userName", "John Doe Updated", "userEmail", "john.doe.updated@example.com", "userType", "ADMIN");
        doNothing().when(userService).updateUser(1L, updates);

        mockMvc.perform(patch("/bank/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"John Doe Updated\",\"userEmail\":\"john.doe.updated@example.com\",\"userType\":\"ADMIN\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("user details updated successfully"));
    }

    @Test
    void deleteUserReturnsSuccessMessage() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/bank/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }
}