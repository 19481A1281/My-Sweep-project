package com.banking.Sweep.service;

import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user);

    @Query(value = "select * from user where user_id =: userId",nativeQuery = true)
    UserDTO retriveUserById(Long userId);


    List<UserDTO> getAllUsers();

    void updateUser(Long userId, Map<String,String> updates);

    void deleteUser(Long userId);

    UserDTO getUserByEmail(String userEmail);
}
