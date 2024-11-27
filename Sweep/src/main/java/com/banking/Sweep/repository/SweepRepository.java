package com.banking.Sweep.repository;

import com.banking.Sweep.model.Sweep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SweepRepository extends JpaRepository<Sweep,Long> {
    @Query(value = "select source_account_number,destination_account_number from sweep",nativeQuery = true)
    List<Map<Long, Long>> findAllSweeps();


}
