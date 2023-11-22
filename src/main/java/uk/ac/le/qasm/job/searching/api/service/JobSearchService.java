package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.enums.JobStatus;
import uk.ac.le.qasm.job.searching.api.repository.JobPostRepository;

import java.util.Set;

@Service
public class JobSearchService implements uk.ac.le.qasm.job.searching.api.adapter.JobSearchService {

    private final JobPostRepository jobPostRepository;

    public JobSearchService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @Override
    public Set<JobPost> searchAll() {
        return jobPostRepository.findAllByIsVisibleAndStatus(true, JobStatus.PENDING);
    }
}
