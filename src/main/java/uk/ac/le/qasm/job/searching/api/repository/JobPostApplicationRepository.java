package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobPostApplication;

import java.util.UUID;

public interface JobPostApplicationRepository extends CrudRepository<JobPostApplication, UUID> {
}
