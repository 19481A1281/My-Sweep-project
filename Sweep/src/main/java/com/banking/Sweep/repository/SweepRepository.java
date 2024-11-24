package com.banking.Sweep.repository;

import com.banking.Sweep.model.Sweep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SweepRepository extends JpaRepository<Sweep,Long> {
}
