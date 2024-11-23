package com.banking.Sweep.service.impl;

import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.model.User;
import com.banking.Sweep.repository.UserRepository;
import com.banking.Sweep.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private ModelMapper modelMapper;

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        userRepository.save(user);

        return user;
    }

    @Override
    public UserDTO retriveUserById(Long userId) {

        User user=userRepository.findById(userId).get();

        return modelMapper.map(user,UserDTO.class);
        //return userRepository.findByDTOId(userId);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList=userRepository.findAll();

        return userList.stream().map(user -> modelMapper.map(user,UserDTO.class)).collect(Collectors.toList());
        //return userRepository.findAllUsers();
    }

    @Override
    public void updateUser(Long userId, Map<String,String> updates) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Update fields dynamically based on provided keys
        if (updates.containsKey("userName")) {
            user.setUserName(updates.get("userName"));
        }
        if (updates.containsKey("userEmail")) {
            user.setUserEmail(updates.get("userEmail"));
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }


}
