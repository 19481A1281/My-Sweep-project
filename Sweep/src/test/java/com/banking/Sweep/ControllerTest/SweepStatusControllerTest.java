package com.banking.Sweep.ControllerTest;


import com.banking.Sweep.DTO.DateRangeDTO;
import com.banking.Sweep.controller.SweepStatusController;
import com.banking.Sweep.model.SweepStatus;
import com.banking.Sweep.service.SweepStatusService;
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

class SweepStatusControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SweepStatusService sweepStatusService;

    @InjectMocks
    private SweepStatusController sweepStatusController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sweepStatusController).build();
    }

    @Test
    void getSweepStatusByDateRangeReturnsListOfSweepStatuses() throws Exception {
        List<SweepStatus> sweepStatuses = Arrays.asList(
                new SweepStatus(1L, 1L, "Completed", null),
                new SweepStatus(2L, 2L, "Failed", "Insufficient funds")
        );
        DateRangeDTO dateRangeDTO = new DateRangeDTO("2023-01-01T00:00:00", "2023-12-31T00:00:00");
        when(sweepStatusService.getSweepStatus(any(DateRangeDTO.class))).thenReturn(sweepStatuses);

        mockMvc.perform(get("/bank/sweep-status/custom-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDate\":\"2023-01-01T00:00:00\",\"endDate\":\"2023-12-31T00:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"statusId\":1,\"sweepId\":1,\"status\":\"Completed\",\"failureReason\":null},{\"statusId\":2,\"sweepId\":2,\"status\":\"Failed\",\"failureReason\":\"Insufficient funds\"}]"));
    }

    @Test
    void getAllSweepStatusesReturnsListOfSweepStatuses() throws Exception {
        List<SweepStatus> sweepStatuses = Arrays.asList(
                new SweepStatus(1L, 1L,"Completed",null),
                new SweepStatus(2L, 2L,"Failed","Insufficient funds")
        );
        when(sweepStatusService.getAllSweepStatus()).thenReturn(sweepStatuses);

        mockMvc.perform(get("/bank/sweep-status/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"statusId\":1,\"sweepId\":1,\"status\":\"Completed\",\"failureReason\":null},{\"statusId\":2,\"sweepId\":2,\"status\":\"Failed\",\"failureReason\":\"Insufficient funds\"}]"));
    }


}
