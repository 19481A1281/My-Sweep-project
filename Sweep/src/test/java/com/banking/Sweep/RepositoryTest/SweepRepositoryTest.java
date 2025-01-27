package com.banking.Sweep.RepositoryTest;

import com.banking.Sweep.model.Sweep;
import com.banking.Sweep.repository.SweepRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class SweepRepositoryTest {

    @Autowired
    private SweepRepository sweepRepository;

    @Test
    void testSaveSweep() {
        Sweep sweep = new Sweep(1L,12345L, 67890L);

        Sweep savedSweep = sweepRepository.save(sweep);

        assertNotNull(savedSweep);
        assertEquals(12345L, savedSweep.getSourceAccount());
        assertEquals(67890L, savedSweep.getDestinationAccount());
    }

    @Test
    void testFindById() {
        Sweep sweep = new Sweep(1L,12345L, 67890L);

        Sweep savedSweep = sweepRepository.save(sweep);
        Optional<Sweep> foundSweep = sweepRepository.findById(savedSweep.getSweepId());

        assertTrue(foundSweep.isPresent());
        assertEquals(12345L, foundSweep.get().getSourceAccount());
        assertEquals(67890L, foundSweep.get().getDestinationAccount());
    }

    @Test
    void testDeleteSweep() {
        Sweep sweep = new Sweep(1L,12345L, 67890L);

        Sweep savedSweep = sweepRepository.save(sweep);
        sweepRepository.deleteById(savedSweep.getSweepId());

        Optional<Sweep> foundSweep = sweepRepository.findById(savedSweep.getSweepId());

        assertFalse(foundSweep.isPresent());
    }

    @Test
    void testFindAllSweeps() {
        Sweep sweep1 = new Sweep(1L,12345L, 67890L);
        Sweep sweep2 = new Sweep(1L,54321L, 98765L);

        sweepRepository.save(sweep1);
        sweepRepository.save(sweep2);

        List<Map<Long, Long>> sweeps = sweepRepository.findAllSweeps();

        assertEquals(2, sweeps.size());
        assertTrue(sweeps.stream().anyMatch(s -> s.get("source_account").equals(12345L) && s.get("destination_account").equals(67890L)));
        assertTrue(sweeps.stream().anyMatch(s -> s.get("source_account").equals(54321L) && s.get("destination_account").equals(98765L)));
    }
}