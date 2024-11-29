package com.banking.Sweep.repository;

import com.banking.Sweep.DTO.UserDTO;
import com.banking.Sweep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user",nativeQuery = true)
    List<UserDTO> findAllUsers();


    @Query(value = "select * from user where user_id =:userId",nativeQuery = true)
    UserDTO findByDTOId(Long userId);

    @Query(value = "select * from user where user_email =:userEmail",nativeQuery = true)
    UserDTO findByEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);
    // Additional custom query methods can be added here

}
