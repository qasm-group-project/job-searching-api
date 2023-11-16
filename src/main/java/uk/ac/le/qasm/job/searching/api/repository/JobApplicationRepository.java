package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;

import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
}
