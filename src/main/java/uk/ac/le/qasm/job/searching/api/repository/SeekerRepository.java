package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.Provider;

import java.util.Optional;
import java.util.UUID;

public interface SeekerRepository extends JpaRepository<JobSeeker, UUID> {
    Optional<JobSeeker> findByUsername(String username);
}
