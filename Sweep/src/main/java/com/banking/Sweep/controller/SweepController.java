package com.banking.Sweep.controller;

import com.banking.Sweep.service.SweepService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank/sweep")
public class SweepController {
    private final SweepService sweepService;

    public SweepController(SweepService sweepService) {
        this.sweepService = sweepService;
    }

    @PatchMapping
    public String startSweep(){
        sweepService.startSweep();
        return "Sweep completed successfully";
    }

}
