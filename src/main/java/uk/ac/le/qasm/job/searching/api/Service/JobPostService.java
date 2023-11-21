package uk.ac.le.qasm.job.searching.api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.repository.JobPostRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class JobPostService {
    private final JobPostRepository jobPostRepository;

    @Autowired
    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public void saveJobPost(JobPost jobPost) {
        jobPostRepository.save(jobPost);
    }
}
