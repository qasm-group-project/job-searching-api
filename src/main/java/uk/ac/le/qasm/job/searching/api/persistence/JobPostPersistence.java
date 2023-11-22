package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.enums.JobStatus;
import uk.ac.le.qasm.job.searching.api.repository.JobPostRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class JobPostPersistence {

    private final JobPostRepository jobPostRepository;

    public JobPostPersistence(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }


    public Set<JobPost> findAllAvailable() {
        return jobPostRepository.findAllByIsVisibleAndStatus(true, JobStatus.PENDING);
    }

    public Optional<JobPost> findAvailableById(UUID jobId) {
        return this.jobPostRepository.findByIdAndIsVisibleAndStatus(jobId, true, JobStatus.PENDING);
    }
}
