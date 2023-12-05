package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.*;
import uk.ac.le.qasm.job.searching.api.enums.JobApplicationStatus;
import uk.ac.le.qasm.job.searching.api.repository.JobApplicationRepository;

import java.time.LocalDateTime;
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

    public Set<JobApplication> findAllByProvider(Provider provider) {
        return this.jobApplicationRepository.findAllByProvider(provider);
    }

    public Optional<JobApplication> findByIdAndSeeker(UUID jobApplicationId, JobSeeker seeker) {
        return this.jobApplicationRepository.findByIdAndApplicantId(jobApplicationId, seeker.getId());
    }

    public void deleteJobApplication(UUID jobApplicationId, JobSeeker jobSeeker) {
        Optional<JobApplication> existingJobApplicationOptional = jobApplicationRepository.findByIdAndApplicantId(
                jobApplicationId, jobSeeker.getId());

        if (existingJobApplicationOptional.isPresent()) {
            JobApplication existingJobApplication = existingJobApplicationOptional.get();

            jobApplicationRepository.deleteJobApplicationByIdAndApplicant(existingJobApplication.getId(), existingJobApplication.getApplicant());
        } else {
            throw new RuntimeException("Job Application not found for the given ID and seeker");
        }
    }

    public void updateJobApplicationStatus(UUID jobApplicationId, Provider provider, JobApplicationStatus jobApplicationStatus) {
        Optional<JobApplication> existingJobApplicationOptional = jobApplicationRepository.findByIdAndProvider(jobApplicationId, provider);

        if (existingJobApplicationOptional.isPresent()) {
            JobApplication existingJobApplication = existingJobApplicationOptional.get();

            jobApplicationRepository.updateJobApplicationStatus(existingJobApplication.getId(), jobApplicationStatus);
        } else {
            throw new RuntimeException("Job Application not found for the given ID and provider");
        }
    }

    public JobApplication updateJobApplication(UUID jobApplicationId, Provider provider, JobApplication updateJobApplication) {
        Optional<JobApplication> existingJobApplicationOptional = jobApplicationRepository.findByIdAndProvider(jobApplicationId, provider);

        if (existingJobApplicationOptional.isPresent()) {
            JobApplication existingJobApplication = existingJobApplicationOptional.get();
            existingJobApplication.setInterview(updateJobApplication.getInterview());
            return jobApplicationRepository.save(existingJobApplication);
        } else {
            throw new RuntimeException("Job Application not found for the given ID and provider");
        }
    }

    public Page<JobApplication> getInterviewsJobApplications(JobSeeker jobSeeker, LocalDateTime currentDateTime, Pageable pageable) {
        return jobApplicationRepository.findByApplicantAndInterviewAfter(jobSeeker, currentDateTime, pageable);
    }
}
