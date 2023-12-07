package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerApplication;
import uk.ac.le.qasm.job.searching.api.repository.JobSeekerApplicationRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class JobSeekerApplicationPersistence {

    private final JobSeekerApplicationRepository jobSeekerApplicationRepository;

    public JobSeekerApplicationPersistence(JobSeekerApplicationRepository jobSeekerApplicationRepository) {
        this.jobSeekerApplicationRepository = jobSeekerApplicationRepository;
    }

    public JobSeekerApplication save(JobSeekerApplication jobSeekerApplication) {
        return this.jobSeekerApplicationRepository.save(jobSeekerApplication);
    }

    public Set<JobSeekerApplication> findAllByApplicant(JobSeeker jobSeeker) {
        return jobSeekerApplicationRepository.findAllByApplicantId(jobSeeker.getId());
    }

    public Optional<JobSeekerApplication> findById(UUID applicationId) {
        return jobSeekerApplicationRepository.findById(applicationId);
    }
}
