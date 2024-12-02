package com.banking.Sweep.service.impl;

import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.Exception.DoesNotExistException;
import com.banking.Sweep.Exception.DuplicateEmailException;
import com.banking.Sweep.Exception.PasswordPatternMissMatchException;
import com.banking.Sweep.model.User;
import com.banking.Sweep.model.UserType;
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
        validatePassword(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public UserDTO retriveUserById(Long userId) {

            User user = userRepository.findById(userId).orElseThrow(()->new DoesNotExistException("User not found with ID: " + userId));
            return modelMapper.map(user, UserDTO.class);

    }

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new DoesNotExistException("User not found with ID: " + userId));
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
        if (updates.containsKey("userType")) {
            user.setUserType(UserType.valueOf(updates.get("userType")));
        }
        if (updates.containsKey("password") && validatePassword(updates.get("password"))) {
            user.setPassword(updates.get("password"));
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
            User user=userRepository.findByUserEmail(userEmail);
            return modelMapper.map(user,UserDTO.class);
        }
        else{
            throw new DoesNotExistException("User not found with email :"+userEmail);
        }
    }

    private boolean validatePassword(String password) {
        if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")){
            return true;
        }
        throw new PasswordPatternMissMatchException("Password must contain at least 8 characters, including uppercase, lowercase, and a number");
    }

}
