package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.UserNotFoundException;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerPersistence;

import java.util.UUID;

@Service
public class JobSeekerService implements uk.ac.le.qasm.job.searching.api.adapter.JobSeekerService {

    private final JobSeekerPersistence jobSeekerPersistence;

    public JobSeekerService(JobSeekerPersistence jobSeekerPersistence) {
        this.jobSeekerPersistence = jobSeekerPersistence;
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
