package uk.ac.le.qasm.job.searching.api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.Enumeration.JobType;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.repository.JobPostRepository;
import uk.ac.le.qasm.job.searching.api.request.JobPostRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JobPostService {
    private final JobPostRepository jobPostRepository;

    @Autowired
    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public ResponseEntity<Object> saveJobPost(JobPost jobPost) {
        jobPostRepository.save(jobPost);
        Map<String, Object> responseObj = new HashMap<String, Object>();
        responseObj.put("message", "Job Post Created successfully!");
        responseObj.put("status", HttpStatus.OK.value());
        return new ResponseEntity<Object>(responseObj, HttpStatus.OK);
    }
    public Page<JobPost> getAllJobPosts(Pageable pageable) {
        return jobPostRepository.findAll(pageable);
    }

    public JobPost updateJobPost(UUID jobPostId, JobPostRequest updatedJobPost) {
        JobPost existingJobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new RuntimeException("Job post not found"));

        // Update fields
        existingJobPost.setTitle(updatedJobPost.getTitle());
        existingJobPost.setDescription(updatedJobPost.getDescription());
        existingJobPost.setSalary(updatedJobPost.getSalary());
        existingJobPost.setIsVisible(updatedJobPost.getIsVisible());
        existingJobPost.setJobType(JobType.valueOf(updatedJobPost.getJobType()));
        // Save and return the updated job post
        return jobPostRepository.save(existingJobPost);
    }

    public ResponseEntity<Object> deleteJobPost(UUID jobPostId) {
        JobPost existingJobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new RuntimeException("Job post not found with id: " + jobPostId));
        jobPostRepository.delete(existingJobPost);
        Map<String, Object> responseObj = new HashMap<String, Object>();
        responseObj.put("message", "Job Post Deleted successfully!");
        responseObj.put("status", HttpStatus.OK.value());
        return new ResponseEntity<Object>(responseObj, HttpStatus.OK);

    }

}
