// File: Sweep/src/test/java/com/banking/Sweep/ServiceTest/SweepServiceTest.java

package com.banking.Sweep.ServiceTest;

import com.banking.Sweep.Exception.DoesNotExistException;
import com.banking.Sweep.model.Sweep;
import com.banking.Sweep.service.SweepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SweepServiceTest {

    @Mock
    private SweepService sweepService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void startSweepSuccess() {
        doNothing().when(sweepService).startSweep();

        sweepService.startSweep();

        verify(sweepService, times(1)).startSweep();
    }

    @Test
    void addSweepSuccess() {
        Sweep sweep = new Sweep();
        sweep.setSweepId(1L);

        doNothing().when(sweepService).addSweep(sweep);

        sweepService.addSweep(sweep);

        verify(sweepService, times(1)).addSweep(sweep);
    }

    @Test
    void getSweepByIdThrowsDoesNotExistException() {
        when(sweepService.getSweepById(1L)).thenThrow(new DoesNotExistException("Sweep not found"));

        assertThrows(DoesNotExistException.class, () -> sweepService.getSweepById(1L));
    }

    @Test
    void getSweepByIdSuccess() {
        Sweep sweep = new Sweep();
        sweep.setSweepId(1L);

        when(sweepService.getSweepById(1L)).thenReturn(sweep);

        Sweep result = sweepService.getSweepById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getSweepId());
    }

    @Test
    void getAllSweepsSuccess() {
        Sweep sweep1 = new Sweep();
        sweep1.setSweepId(1L);
        Sweep sweep2 = new Sweep();
        sweep2.setSweepId(2L);
        List<Sweep> sweeps = Arrays.asList(sweep1, sweep2);

        when(sweepService.getAllSweeps()).thenReturn(sweeps);

        List<Sweep> result = sweepService.getAllSweeps();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}