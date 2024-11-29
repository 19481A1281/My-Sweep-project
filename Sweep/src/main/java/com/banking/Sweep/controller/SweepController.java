package com.banking.Sweep.controller;

import com.banking.Sweep.DTO.SweepDTO;
import com.banking.Sweep.model.Sweep;
import com.banking.Sweep.service.SweepService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/sweep")
public class SweepController {
    private final SweepService sweepService;

    public SweepController(SweepService sweepService) {
        this.sweepService = sweepService;
    }

    @PostMapping()
    public String addSweep(@RequestBody Sweep sweep){
        sweepService.addSweep(sweep);
        return "Sweep added successfully";
    }

    @Scheduled(cron = "45 55 21 * * ?")
    @PatchMapping
    public String startSweep(){
        sweepService.startSweep();
        return "Sweep completed successfully";
    }

}
