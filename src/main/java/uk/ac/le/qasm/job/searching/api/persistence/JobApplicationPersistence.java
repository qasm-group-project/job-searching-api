package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.repository.JobApplicationRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class JobApplicationPersistence {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationPersistence(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public JobApplication save(JobApplication jobApplication) {
        return this.jobApplicationRepository.save(jobApplication);
    }

    public Set<JobApplication> findAllByApplicantId(UUID seekerId) {
        return this.jobApplicationRepository.findAllByApplicantId(seekerId);
    }

    public Optional<JobApplication> findByIdAndSeeker(UUID jobApplicationId, JobSeeker seeker) {
        return this.jobApplicationRepository.findByIdAndSeeker(jobApplicationId, seeker);
    }

    public void deleteJobApplication(UUID jobApplicationId, JobSeeker jobSeeker) {
        Optional<JobApplication> existingJopApplicationOptional = jobApplicationRepository.findByIdAndSeeker(
                jobApplicationId, jobSeeker);

        if (existingJopApplicationOptional.isPresent()) {
            JobApplication existingJobApplication = existingJopApplicationOptional.get();
            jobApplicationRepository.delete(existingJobApplication);
        } else {
            throw new RuntimeException("Job Application not found for the given ID and seeker");
        }
    }
}
