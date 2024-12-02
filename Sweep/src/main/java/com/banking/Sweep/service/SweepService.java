package com.banking.Sweep.service;

import com.banking.Sweep.DTO.SweepDTO;
import com.banking.Sweep.model.Sweep;

import java.util.List;

public interface SweepService {
    void startSweep();

    void addSweep(Sweep sweep);

    Sweep getSweepById(Long sweepId);

    List<Sweep> getAllSweeps();
}
