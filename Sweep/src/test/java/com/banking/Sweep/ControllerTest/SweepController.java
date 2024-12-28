package com.banking.Sweep.ControllerTest;


import com.banking.Sweep.DTO.SweepDTO;
import com.banking.Sweep.controller.SweepController;
import com.banking.Sweep.model.Sweep;
import com.banking.Sweep.service.SweepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SweepControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SweepService sweepService;

    @InjectMocks
    private SweepController sweepController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sweepController).build();
    }

    @Test
    void addSweepReturnsSuccessMessage() throws Exception {
        Sweep sweep = new Sweep(1L, 123L, 456L);
        doNothing().when(sweepService).addSweep(any(Sweep.class));

        mockMvc.perform(post("/bank/sweep")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sweepId\":1,\"sourceAccount\":123,\"destinationAccount\":456}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sweep added successfully"));
    }

    @Test
    void getSweepByIdReturnsSweep() throws Exception {
        Sweep sweep = new Sweep(1L, 123L, 456L);
        when(sweepService.getSweepById(1L)).thenReturn(sweep);

        mockMvc.perform(get("/bank/sweep/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sweepId\":1,\"sourceAccount\":123,\"destinationAccount\":456}"));
    }

    @Test
    void getAllSweepsReturnsListOfSweeps() throws Exception {
        List<Sweep> sweeps = Arrays.asList(
                new Sweep(1L, 123L, 456L),
                new Sweep(2L, 789L, 101L)
        );
        when(sweepService.getAllSweeps()).thenReturn(sweeps);

        mockMvc.perform(get("/bank/sweep")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"sweepId\":1,\"sourceAccount\":123,\"destinationAccount\":456},{\"sweepId\":2,\"sourceAccount\":789,\"destinationAccount\":101}]"));
    }

    @Test
    void startSweepReturnsSuccessMessage() throws Exception {
        doNothing().when(sweepService).startSweep();

        mockMvc.perform(patch("/bank/sweep")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Sweep completed successfully"));
    }
}
