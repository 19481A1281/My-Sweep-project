// File: Sweep/src/test/java/com/banking/Sweep/ServiceTest/SweepStatusServiceTest.java

package com.banking.Sweep.ServiceTest;

import com.banking.Sweep.DTO.DateRangeDTO;
import com.banking.Sweep.model.SweepStatus;
import com.banking.Sweep.service.SweepStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SweepStatusServiceTest {

    @Mock
    private SweepStatusService sweepStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSweepStatusSuccess() {
        SweepStatus sweepStatus = new SweepStatus();
        sweepStatus.setStatusId(1L);

        doNothing().when(sweepStatusService).createSweepStatus(sweepStatus);

        sweepStatusService.createSweepStatus(sweepStatus);

        verify(sweepStatusService, times(1)).createSweepStatus(sweepStatus);
    }

    @Test
    void getAllSweepStatusSuccess() {
        SweepStatus sweepStatus1 = new SweepStatus();
        sweepStatus1.setStatusId(1L);
        SweepStatus sweepStatus2 = new SweepStatus();
        sweepStatus2.setStatusId(2L);
        List<SweepStatus> sweepStatuses = Arrays.asList(sweepStatus1, sweepStatus2);

        when(sweepStatusService.getAllSweepStatus()).thenReturn(sweepStatuses);

        List<SweepStatus> result = sweepStatusService.getAllSweepStatus();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getSweepStatusByDateRangeSuccess() {
        DateRangeDTO dateRangeDTO = new DateRangeDTO();
        SweepStatus sweepStatus1 = new SweepStatus();
        sweepStatus1.setStatusId(1L);
        SweepStatus sweepStatus2 = new SweepStatus();
        sweepStatus2.setStatusId(2L);
        List<SweepStatus> sweepStatuses = Arrays.asList(sweepStatus1, sweepStatus2);

        when(sweepStatusService.getSweepStatus(dateRangeDTO)).thenReturn(sweepStatuses);

        List<SweepStatus> result = sweepStatusService.getSweepStatus(dateRangeDTO);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}