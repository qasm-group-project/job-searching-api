package uk.ac.le.qasm.job.searching.api.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.UserNotFoundException;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerPersistence;
import uk.ac.le.qasm.job.searching.api.service.JobSeekerService;

import java.util.UUID;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerPersistence jobSeekerPersistence;

    public JobSeekerServiceImpl( JobSeekerPersistence jobSeekerPersistence) {
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
