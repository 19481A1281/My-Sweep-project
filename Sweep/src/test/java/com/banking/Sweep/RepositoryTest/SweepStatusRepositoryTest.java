package com.banking.Sweep.RepositoryTest;

import com.banking.Sweep.model.SweepStatus;
import com.banking.Sweep.repository.SweepStatusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class SweepStatusRepositoryTest {

    @Autowired
    private SweepStatusRepository sweepStatusRepository;

    @Test
    void testSaveSweepStatus() {
        SweepStatus sweepStatus = new SweepStatus(28L, 1L, "Success", "No Failure", LocalDateTime.now());

        SweepStatus savedSweepStatus = sweepStatusRepository.save(sweepStatus);

        assertNotNull(savedSweepStatus);
        assertEquals("Success", savedSweepStatus.getStatus());
    }

    @Test
    void testFindById() {
        SweepStatus sweepStatus = new SweepStatus(28L, 1L, "Success", "No Failure",LocalDateTime.now());

        SweepStatus savedSweepStatus = sweepStatusRepository.save(sweepStatus);
        Optional<SweepStatus> foundSweepStatus = sweepStatusRepository.findById(savedSweepStatus.getStatusId());

        assertTrue(foundSweepStatus.isPresent());
        assertEquals("Success", foundSweepStatus.get().getStatus());
    }

    @Test
    void testDeleteSweepStatus() {
        SweepStatus sweepStatus = new SweepStatus(28L, 1L, "Success", "No Failure",LocalDateTime.now());

        SweepStatus savedSweepStatus = sweepStatusRepository.save(sweepStatus);
        sweepStatusRepository.deleteById(savedSweepStatus.getStatusId());

        Optional<SweepStatus> foundSweepStatus = sweepStatusRepository.findById(savedSweepStatus.getStatusId());

        assertFalse(foundSweepStatus.isPresent());
    }

    @Test
    void testUpdateSweepStatus() {
        SweepStatus sweepStatus = new SweepStatus(28L, 1L, "Success", "No Failure",LocalDateTime.now());

        SweepStatus savedSweepStatus = sweepStatusRepository.save(sweepStatus);
        savedSweepStatus.setStatus("Success");
        SweepStatus updatedSweepStatus = sweepStatusRepository.save(savedSweepStatus);

        assertNotNull(updatedSweepStatus);
        assertEquals("Success", updatedSweepStatus.getStatus());
    }
}