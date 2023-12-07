package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobPostApplication;
import uk.ac.le.qasm.job.searching.api.repository.JobPostApplicationRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class JobPostApplicationPersistence {

    private final JobPostApplicationRepository jobPostApplicationRepository;

    public JobPostApplicationPersistence(JobPostApplicationRepository jobPostApplicationRepository) {
        this.jobPostApplicationRepository = jobPostApplicationRepository;
    }

    public JobPostApplication save(JobPostApplication jobPostApplication) {
        return jobPostApplicationRepository.save(jobPostApplication);
    }

    public Optional<JobPostApplication> findById(UUID applicationId) {
        return jobPostApplicationRepository.findById(applicationId);
    }

}
