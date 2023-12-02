package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerApplication;

import java.util.Set;
import java.util.UUID;

public interface JobSeekerApplicationRepository extends CrudRepository<JobSeekerApplication, UUID> {
    Set<JobSeekerApplication> findAllByApplicantId(UUID jobSeekerId);
}
