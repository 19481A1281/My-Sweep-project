package com.banking.Sweep.controller;

import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.model.User;
import com.banking.Sweep.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("bank/user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String registerUser(@RequestBody User user){
       userService.createUser(user);
       return "User registered successfully";
    }

   @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId){
        return userService.retriveUserById(userId);
   }

   @GetMapping("email/{userEmail}")
   public UserDTO getUserByEmail(@PathVariable String userEmail){
        return userService.getUserByEmail(userEmail);
   }

   @GetMapping
   public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
   }

   // All key-value pairs in the JSON are captured by the Map, so you can handle updates for multiple fields simultaneously.
   @PatchMapping("{userId}")
    public String updateUser(@PathVariable Long userId, @RequestBody Map<String,String> updates){
        userService.updateUser(userId,updates);
        return "user details updated successfully";
   }

   @DeleteMapping("{userId}")
    public String deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return "User deleted successfully";
   }

}

// All key-value pairs in the JSON are captured by the Map, so you can handle updates for multiple fields simultaneously.
/*@PatchMapping is a Spring annotation used to handle HTTP PATCH requests in a RESTful web application. The PATCH method is specifically designed for partial updates to a resource. Instead of replacing the entire resource (like a PUT request), PATCH allows you to send only the fields that need to be updated.

When to Use @PatchMapping
When you want to update specific fields in an entity without affecting the rest of the entity.
When you don't want to send the entire resource representation in the request body.*/


