package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface JobApplicationRepository extends CrudRepository<JobApplication, UUID> {
    Set<JobApplication> findAllByJobPostId(UUID jobPostId);

    Set<JobApplication> findAllByApplicantId(UUID jobSeekerId);

    Optional<JobApplication> findByIdAndApplicant(UUID jobApplicationId, JobSeeker seeker);
}
