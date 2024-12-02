package com.banking.Sweep.controller;

import com.banking.Sweep.DTO.SweepDTO;
import com.banking.Sweep.model.Sweep;
import com.banking.Sweep.service.SweepService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("{sweepId}")
    public Sweep getSweepById(@PathVariable Long sweepId){
        return sweepService.getSweepById(sweepId);
    }

    public List<Sweep> getAllSweeps(){
        return sweepService.getAllSweeps();
    }

    @Scheduled(cron = "30 38 19 * * ?")
    @PatchMapping
    public String startSweep(){
        sweepService.startSweep();
        return "Sweep completed successfully";
    }

}
