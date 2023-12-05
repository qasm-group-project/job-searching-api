package uk.ac.le.qasm.job.searching.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.adapter.JobSearchService;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.enums.JobApplicationStatus;
import uk.ac.le.qasm.job.searching.api.persistence.JobApplicationPersistence;
import uk.ac.le.qasm.job.searching.api.service.JobPostService;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/seeker/job-posts")
public class SeekerJobPostController {

    private final JobSearchService jobSearchService;
    private final JobApplicationPersistence jobApplicationPersistence;
    private final JobPostService jobPostService;

    public SeekerJobPostController(JobSearchService jobSearchService, JobApplicationPersistence jobApplicationPersistence, JobPostService jobPostService) {
        this.jobSearchService = jobSearchService;
        this.jobApplicationPersistence = jobApplicationPersistence;
        this.jobPostService = jobPostService;
    }

    @GetMapping
    public ResponseEntity<Set<JobPost>> searchJobs() {
        return ResponseEntity.ok(jobSearchService.searchAll());
    }

    @PostMapping("/{job_id}/apply")
    public ResponseEntity<JobApplication> applyForJob(@PathVariable("job_id") UUID jobId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();

        JobPost jobPost = jobSearchService.findById(jobId);

        JobApplication jobApplication = JobApplication.builder()
                                                      .applicant(jobSeeker)
                                                      .jobPost(jobPost)
                                                      .status(JobApplicationStatus.PENDING)
                                                      .build();
        return ResponseEntity.ok(jobApplicationPersistence.save(jobApplication));
    }

    @GetMapping("/deadlines")
    public ResponseEntity<Page<JobPost>> getDeadlinesJobPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            Page<JobPost> deadlinesJobPosts = jobPostService.getDeadlinesJobPosts(currentDateTime, PageRequest.of(page, size));

            return new ResponseEntity<>(deadlinesJobPosts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
