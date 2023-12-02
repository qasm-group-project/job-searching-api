package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.JobPostRequest;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.enums.JobType;
import uk.ac.le.qasm.job.searching.api.repository.JobPostRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobPostService {
    private final JobPostRepository jobPostRepository;
    private final CsvFileWriterService csvFileWriterService;

    @Autowired
    public JobPostService(JobPostRepository jobPostRepository, CsvFileWriterService csvFileWriterService) {
        this.jobPostRepository = jobPostRepository;
        this.csvFileWriterService = csvFileWriterService;
    }

    public JobPost saveJobPost(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    public Page<JobPost> getAllJobPostsByProvider(Provider provider, PageRequest pageRequest) {
        return jobPostRepository.findByProvider(provider, pageRequest);
    }

    public byte[] getAllJobPostsByProviderInFile(Provider provider) {
        Set<JobPost> jobPosts = jobPostRepository.findAllByProvider(provider);

        return csvFileWriterService.generate(jobPosts.stream()
                                                     .peek(jobPost -> jobPost.setJobPostApplications(null))
                                                     .sorted(Comparator.comparing(JobPost::getTitle))
                                                     .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    public JobPost updateJobPost(Provider provider, UUID jobPostId, JobPostRequest updatedJobPost) {
        JobPost existingJobPost = jobPostRepository.findById(jobPostId)
                                                   .orElseThrow(() -> new RuntimeException("Job post not found"));
        if (!existingJobPost.getProvider().getId().equals(provider.getId())) {
            throw new RuntimeException("You are not authorized to update this job post");
        }

        existingJobPost.setTitle(updatedJobPost.getTitle());
        existingJobPost.setDescription(updatedJobPost.getDescription());
        existingJobPost.setSalary(updatedJobPost.getSalary());
        existingJobPost.setIsVisible(updatedJobPost.getIsVisible());
        existingJobPost.setJobType(JobType.valueOf(updatedJobPost.getJobType()));
        existingJobPost.setStatus(updatedJobPost.getJobStatus());
        existingJobPost.setDeadline(updatedJobPost.getDeadline());

        return jobPostRepository.save(existingJobPost);
    }

    public ResponseEntity<Object> deleteJobPost(Provider provider, UUID jobPostId) {
        JobPost existingJobPost = jobPostRepository.findById(jobPostId)
                                                   .orElseThrow(() -> new RuntimeException("Job post not found"));
        if (!existingJobPost.getProvider().getId().equals(provider.getId())) {
            throw new RuntimeException("You are not authorized to delete this job post");
        }

        jobPostRepository.delete(existingJobPost);
        Object responseBody = Map.of("message", "Job post deleted successfully");
        return ResponseEntity.ok(responseBody);
    }

    public Page<JobPost> getExpiredJobPosts(Provider provider, LocalDateTime currentDateTime, Pageable pageable) {
        return jobPostRepository.findByProviderAndDeadlineBefore(provider, currentDateTime, pageable);
    }
}
