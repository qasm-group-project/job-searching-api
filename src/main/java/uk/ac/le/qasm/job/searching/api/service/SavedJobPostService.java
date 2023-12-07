package uk.ac.le.qasm.job.searching.api.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.SeekerSavedJobPost;
import uk.ac.le.qasm.job.searching.api.repository.JobPostRepository;
import uk.ac.le.qasm.job.searching.api.repository.SavedJobPostRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class SavedJobPostService {
    private final SavedJobPostRepository savedJobPostRepository;
    private final JobPostRepository jobPostRepository;


    public SavedJobPostService(SavedJobPostRepository savedJobPostRepository, JobPostRepository jobPostRepository) {
        this.savedJobPostRepository = savedJobPostRepository;
        this.jobPostRepository = jobPostRepository;
    }

    public SeekerSavedJobPost saveJobPost(UUID jobId, JobSeeker jobSeeker) {
        JobPost existingJobPost = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job post not found"));
        Optional<SeekerSavedJobPost> existingApplication =
                savedJobPostRepository.findByJobSeekerAndJobPost(jobSeeker, existingJobPost);
        if (existingApplication.isPresent()){
            throw new RuntimeException("Job Post already saved!");
        }
        SeekerSavedJobPost savedJobPost = new SeekerSavedJobPost();
        savedJobPost.setJobPost(existingJobPost);
        savedJobPost.setJobSeeker(jobSeeker);
        return savedJobPostRepository.save(savedJobPost);
    }
    public void deleteJobPost(UUID SavedJobId, JobSeeker jobSeeker) {
        SeekerSavedJobPost existingJobPost = savedJobPostRepository.findById(SavedJobId)
                .orElseThrow(() -> new RuntimeException("Saved Job post not found"));
        if (!existingJobPost.getJobSeeker().getId().equals(jobSeeker.getId())) {
            throw new RuntimeException("You are not authorized to delete this saved job post");
        }
        savedJobPostRepository.delete(existingJobPost);
    }

    public Page<SeekerSavedJobPost> getSavedJobPostsByJobSeeker(JobSeeker jobSeeker, Pageable pageable) {
        return savedJobPostRepository.findByJobSeeker(jobSeeker, pageable);
    }
}
