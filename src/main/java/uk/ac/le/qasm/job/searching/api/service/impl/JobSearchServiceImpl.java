package uk.ac.le.qasm.job.searching.api.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.enums.JobStatus;
import uk.ac.le.qasm.job.searching.api.repository.JobPostRepository;
import uk.ac.le.qasm.job.searching.api.service.JobSearchService;

import java.util.Set;

@Service
public class JobSearchServiceImpl implements JobSearchService {

    private final JobPostRepository jobPostRepository;

    public JobSearchServiceImpl(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @Override
    public Set<JobPost> searchAll() {
        return jobPostRepository.findAllByIsVisibleAndStatus(true, JobStatus.PENDING);
    }
}
