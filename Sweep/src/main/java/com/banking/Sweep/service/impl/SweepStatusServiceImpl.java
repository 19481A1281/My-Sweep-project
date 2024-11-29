package com.banking.Sweep.service.impl;

import com.banking.Sweep.DTO.DateRangeDTO;
import com.banking.Sweep.model.SweepStatus;
import com.banking.Sweep.repository.SweepStatusRepository;
import com.banking.Sweep.service.SweepStatusService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SweepStatusServiceImpl implements SweepStatusService {


    private final SweepStatusRepository sweepStatusRepository;

    public SweepStatusServiceImpl(SweepStatusRepository sweepStatusRepository) {

        this.sweepStatusRepository = sweepStatusRepository;
    }

    @Override
    public void createSweepStatus(SweepStatus sweepStatus) {
        sweepStatusRepository.save(sweepStatus);
    }

    @Override
    public List<SweepStatus> getAllSweepStatus() {
        return sweepStatusRepository.findAll();
    }

    @Override
    public List<SweepStatus> getSweepStatus(DateRangeDTO dateRangeDTO) {
        return sweepStatusRepository.findAllByTimeStampBetween(LocalDateTime.parse(dateRangeDTO.getStartDate()), LocalDateTime.parse(dateRangeDTO.getEndDate()));
    }

}
