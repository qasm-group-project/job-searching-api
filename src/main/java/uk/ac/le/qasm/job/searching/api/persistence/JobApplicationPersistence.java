package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;
import uk.ac.le.qasm.job.searching.api.repository.JobApplicationRepository;

@Component
public class JobApplicationPersistence {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationPersistence(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public JobApplication save(JobApplication jobApplication) {
        return this.jobApplicationRepository.save(jobApplication);
    }

}
