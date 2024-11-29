package com.banking.Sweep.service.impl;

import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.Exception.DoesNotExistException;
import com.banking.Sweep.Exception.DuplicateEmailException;
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
    public void createUser(User user) {
        if(userRepository.existsByUserEmail(user.getUserEmail())){
            throw new DuplicateEmailException("The email address " + user.getUserEmail() + " is already in use.");
        }
        userRepository.save(user);
    }

    @Override
    public UserDTO retriveUserById(Long userId) {

            User user = userRepository.findById(userId).orElseThrow(()->new DoesNotExistException("User not found with ID: " + userId));
            return modelMapper.map(user, UserDTO.class);

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
                .orElseThrow(() -> new DoesNotExistException("User not found with ID: " + userId));

        // Update fields dynamically based on provided keys
        if (updates.containsKey("userName")) {
            user.setUserName(updates.get("userName"));
        }
        if (updates.containsKey("userEmail")) {
            if(userRepository.existsByUserEmail(updates.get("userEmail")) && !user.getUserEmail().equals(updates.get("userEmail"))){
                throw new DuplicateEmailException("The email address " + updates.get("userEmail") + " is already in use.");
            }
            user.setUserEmail(updates.get("userEmail"));
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
        }
        else{
            throw new DoesNotExistException("User not found with ID: " +userId+" to delete");
        }

    }

    @Override
    public UserDTO getUserByEmail(String userEmail) {
        if(userRepository.existsByUserEmail(userEmail)) {
            return userRepository.findByEmail(userEmail);
        }
        else{
            throw new DoesNotExistException("User not found with email :"+userEmail);
        }
    }


}
