package com.banking.Sweep.service;

import com.banking.Sweep.DTO.DateRangeDTO;
import com.banking.Sweep.model.SweepStatus;

import java.util.List;

public interface SweepStatusService {
    void createSweepStatus(SweepStatus sweepStatus);

    List<SweepStatus> getAllSweepStatus();

    List<SweepStatus> getSweepStatus(DateRangeDTO dateRangeDTO);
}
