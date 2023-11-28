package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.UserNotFoundException;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerPersistence;
import uk.ac.le.qasm.job.searching.api.repository.JobApplicationRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class JobSeekerService implements uk.ac.le.qasm.job.searching.api.adapter.JobSeekerService {

    private final JobSeekerPersistence jobSeekerPersistence;
    private final JobApplicationRepository jobApplicationRepository;

    public JobSeekerService(JobSeekerPersistence jobSeekerPersistence, JobApplicationRepository jobApplicationRepository) {
        this.jobSeekerPersistence = jobSeekerPersistence;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Override
    public JobSeeker findByUsername(String username) {
        return jobSeekerPersistence.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public JobSeeker findById(UUID id) {
        return jobSeekerPersistence.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public JobSeeker update(JobSeeker jobSeeker) {
        return jobSeekerPersistence.update(jobSeeker);
    }
}
