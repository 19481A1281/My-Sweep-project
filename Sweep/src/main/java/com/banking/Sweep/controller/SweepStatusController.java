package com.banking.Sweep.controller;

import com.banking.Sweep.DTO.GetSweepStatusDTO;
import com.banking.Sweep.model.SweepStatus;
import com.banking.Sweep.service.SweepStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/sweep-status")
public class SweepStatusController {

    private final SweepStatusService sweepStatusService;

    public SweepStatusController(SweepStatusService sweepStatusService) {
        this.sweepStatusService = sweepStatusService;
    }

    @GetMapping("/custom-date")
    public List<SweepStatus> getSweepStatus(@RequestBody GetSweepStatusDTO getSweepStatusDTO){
        return sweepStatusService.getSweepStatus(getSweepStatusDTO);
    }

    @GetMapping("/all")
    public List<SweepStatus> getAllSweepStatus(){
       return  sweepStatusService.getAllSweepStatus();
    }

    @PostMapping
    public void createSweepStatus(@RequestBody SweepStatus sweepStatus){
        sweepStatusService.createSweepStatus(sweepStatus);
    }
}
