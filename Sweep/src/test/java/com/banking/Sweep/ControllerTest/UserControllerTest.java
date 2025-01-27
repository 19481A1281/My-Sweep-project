package com.banking.Sweep.ControllerTest;



import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.controller.UserController;
import com.banking.Sweep.model.User;
import com.banking.Sweep.model.UserType;
import com.banking.Sweep.service.UserService;
import org.junit.jupiter.api.AfterEach;
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

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable=MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
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

/*

This program is a test suite for the `UserController` class in a Spring Boot application. It uses the JUnit 5 framework and Mockito to perform unit tests for various REST API endpoints. The test class also leverages Spring MVC's `MockMvc` to simulate HTTP requests and verify responses.

Here’s a detailed explanation:

### **Classes and Annotations**
1. **`@Test`**
   Marks a method as a test case that JUnit should execute.

2. **`@BeforeEach`**
   Runs before each test method to set up any preconditions required by the test. Here, it initializes mocks and sets up `MockMvc`.

3. **`@Mock`**
   Creates a mock object for the `UserService` class. This mock will simulate the behavior of the real service layer without actually executing it.

4. **`@InjectMocks`**
   Injects the mocked dependencies (like `userService`) into the `UserController` object. This ensures that the controller being tested is working with mock services.

5. **`MockMvc`**
   A Spring MVC testing utility that allows the simulation of HTTP requests to test controllers in isolation.


6. **`@AfterEach`**
   Runs after each test method to clean up any resources used during the test. Here, it closes the `Mockito` mocks.
---

### **Purpose and Explanation of Each Test**

#### 1. `createUserReturnsSuccessMessage`
- **Purpose**: Verifies the `/bank/user` POST endpoint for user creation.
- **Setup**:
  - A `User` object is created as input.
  - The `userService.createUser()` method is mocked to do nothing (`doNothing()`).
- **Execution**:
  - Sends a POST request with JSON content representing the user.
- **Validation**:
  - Checks if the status is `200 OK` and the response body is `"User registered successfully"`.
- **Annotations in Use**:
  - `@Test`: Defines the test case.
  - `@Mock`: Ensures the service layer behavior is mocked for isolation.

#### 2. `getUserByIdReturnsUser`
- **Purpose**: Tests the `/bank/user/{id}` GET endpoint for retrieving a user by ID.
- **Setup**:
  - A `UserDTO` mock object is created.
  - The `userService.retriveUserById()` method is mocked to return this DTO.
- **Execution**:
  - Sends a GET request for a specific user ID.
- **Validation**:
  - Asserts that the status is `200 OK`.
  - Verifies the response JSON matches the expected `UserDTO`.
- **Annotations in Use**:
  - `@Test` and `@Mock`: As described above.

#### 3. `getAllUsersReturnsListOfUsers`
- **Purpose**: Tests the `/bank/user` GET endpoint to fetch all users.
- **Setup**:
  - A list of `UserDTO` objects is mocked.
  - The `userService.getAllUsers()` method is mocked to return this list.
- **Execution**:
  - Sends a GET request to the `/bank/user` endpoint.
- **Validation**:
  - Asserts that the response status is `200 OK`.
  - Checks if the response body matches the expected list of users.
- **Annotations in Use**:
  - Same as above.

#### 4. `updateUserReturnsSuccessMessage`
- **Purpose**: Verifies the `/bank/user/{id}` PATCH endpoint for updating user details.
- **Setup**:
  - A map of updates (key-value pairs) is prepared.
  - The `userService.updateUser()` method is mocked to do nothing.
- **Execution**:
  - Sends a PATCH request with JSON content containing the updates.
- **Validation**:
  - Checks if the status is `200 OK` and the response body is `"user details updated successfully"`.
- **Annotations in Use**:
  - Same as above.

#### 5. `deleteUserReturnsSuccessMessage`
- **Purpose**: Tests the `/bank/user/{id}` DELETE endpoint for deleting a user.
- **Setup**:
  - The `userService.deleteUser()` method is mocked to do nothing.
- **Execution**:
  - Sends a DELETE request for a specific user ID.
- **Validation**:
  - Asserts that the status is `200 OK` and the response body is `"User deleted successfully"`.
- **Annotations in Use**:
  - Same as above.

---

### **How It Works**
1. **`MockitoAnnotations.openMocks(this)`**:
   - Initializes the mocked objects (`@Mock`) and injects them into the `@InjectMocks` annotated class (`UserController`).

2. **`MockMvcBuilders.standaloneSetup(userController).build()`**:
   - Creates a `MockMvc` instance that simulates HTTP requests to the `UserController`.

3. **Mocking Service Layer**:
   - Each service method (`createUser`, `retriveUserById`, etc.) is mocked to return predefined responses or perform no operation.

4. **Simulating HTTP Requests**:
   - HTTP methods (`POST`, `GET`, `PATCH`, `DELETE`) are tested using `MockMvcRequestBuilders`.
   - JSON content and HTTP headers are included as needed.

5. **Validation**:
   - `andExpect()` methods are used to check the HTTP status code and response content for correctness.

This test class ensures that the `UserController` behaves as expected when interacting with the service layer, simulating realistic
REST API interactions while isolating the controller from actual business logic.
 */