package com.banking.Sweep.service;

import com.banking.Sweep.DTO.SweepDTO;
import com.banking.Sweep.model.Sweep;

public interface SweepService {
    void startSweep();

    void addSweep(Sweep sweep);
}
