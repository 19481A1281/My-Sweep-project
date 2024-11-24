package com.banking.Sweep.repository;

import com.banking.Sweep.DTO.AccountDTO;
import com.banking.Sweep.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {


    //@Query(value = "select account_number,account_type,balance,opt_for_sweep from account where user_id =:userId",nativeQuery = true)
    @Query(value = "select * from account where user_id =:userId",nativeQuery = true)
    List<Account> findAllAccountsByUserId(@Param("userId") Long userId);

    List<Account> findByOptForSweepTrue();
}
