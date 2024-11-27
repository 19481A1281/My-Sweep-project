package com.banking.Sweep.repository;

import com.banking.Sweep.model.SweepStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SweepStatusRepository extends JpaRepository<SweepStatus,Long> {

    //@Query(value = "select * from sweep_status_repository where ")
    List<SweepStatus> findAllByTimeStampBetween(LocalDateTime startDate, LocalDateTime endDate);
}
