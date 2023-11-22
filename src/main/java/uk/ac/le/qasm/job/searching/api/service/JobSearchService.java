package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.exception.JobPostNotFoundException;
import uk.ac.le.qasm.job.searching.api.persistence.JobPostPersistence;

import java.util.Set;
import java.util.UUID;

@Service
public class JobSearchService implements uk.ac.le.qasm.job.searching.api.adapter.JobSearchService {

    private final JobPostPersistence jobPostPersistence;

    public JobSearchService(JobPostPersistence jobPostPersistence) {
        this.jobPostPersistence = jobPostPersistence;
    }

    @Override
    public Set<JobPost> searchAll() {
        return jobPostPersistence.findAllAvailable();
    }

    @Override
    public JobPost findById(UUID jobId) {
        return jobPostPersistence.findAvailableById(jobId).orElseThrow(JobPostNotFoundException::new);
    }
}
