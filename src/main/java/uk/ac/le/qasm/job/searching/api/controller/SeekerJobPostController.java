package uk.ac.le.qasm.job.searching.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.adapter.JobSearchService;
import uk.ac.le.qasm.job.searching.api.entity.*;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerApplicationPersistence;
import uk.ac.le.qasm.job.searching.api.service.ApplicationService;
import uk.ac.le.qasm.job.searching.api.service.JobSeekerService;
import uk.ac.le.qasm.job.searching.api.service.SavedJobPostService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/seeker/job-posts")
public class SeekerJobPostController {

    private final JobSearchService jobSearchService;
    private final JobSeekerService jobSeekerService;
    private final SavedJobPostService savedJobPostService;
    private final JobSeekerApplicationPersistence jobSeekerApplicationPersistence;
    private final ApplicationService applicationService;

    public SeekerJobPostController(JobSearchService jobSearchService,
                                   JobSeekerService jobSeekerService,
                                   SavedJobPostService savedJobPostService,
                                   JobSeekerApplicationPersistence jobSeekerApplicationPersistence, ApplicationService applicationService) {
        this.jobSearchService = jobSearchService;
        this.jobSeekerService = jobSeekerService;
        this.savedJobPostService = savedJobPostService;
        this.jobSeekerApplicationPersistence = jobSeekerApplicationPersistence;
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<Set<JobPost>> searchJobs() {
        return ResponseEntity.ok(jobSearchService.searchAll());
    }

    @PostMapping("/{job_id}/apply")
    public ResponseEntity<JobSeekerApplication> applyForJob(@PathVariable("job_id") UUID jobId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();

        JobPost jobPost = jobSearchService.findById(jobId);

        JobSeekerApplication jobSeekerApplication = JobSeekerApplication.builder()
                                                                        .applicant(jobSeeker)
                                                                        .jobPost(jobPost)
                                                                        .build();
        return ResponseEntity.ok(jobSeekerApplicationPersistence.save(jobSeekerApplication));
    }

    @GetMapping("/applications/csv")
    public ResponseEntity<ByteArrayResource> exportApplications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();

        byte[] resourceCsv = jobSeekerService.getAllJobApplicationsBySeekerInFile(jobSeeker);
        ByteArrayResource resource = new ByteArrayResource(resourceCsv);

        return ResponseEntity.status(HttpStatus.OK)
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=job_applications_" + LocalDate.now() + ".csv")
                             .body(resource);
    }

    @GetMapping("/saved")
    public ResponseEntity<Object> getSavedJobPosts(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();

        Page<SeekerSavedJobPost> savedJobPosts = savedJobPostService.getSavedJobPostsByJobSeeker(jobSeeker, PageRequest.of(page, size));

        Object responseBody = Map.of("data", savedJobPosts.getContent(), "page", savedJobPosts.getNumber(),
                "size", savedJobPosts.getSize(), "totalElements", savedJobPosts.getTotalElements());

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/{job_id}/save")
    public ResponseEntity<?> saveJob(@PathVariable("job_id") UUID jobId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();
        try {
            var savedJobPost = savedJobPostService.saveJobPost(jobId, jobSeeker);
            Object responseBody = Map.of("message", "Job Post Saved successfully!", "id", savedJobPost.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (RuntimeException e) {
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }

    @DeleteMapping("/{saved_job_id}/deleteSavedJobPost")
    public ResponseEntity<?> deleteSavedJobPost(@PathVariable("saved_job_id") UUID jobId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();
        try {
            savedJobPostService.deleteJobPost(jobId, jobSeeker);
            Object responseBody = Map.of("message", "Job Post Removed from your saved posts successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (RuntimeException e) {
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }

    @PostMapping("/applications/{application_id}/feedback")
    public ResponseEntity<Object> sendFeedback(@PathVariable("application_id") UUID applicationId, @RequestBody SeekerFeedback seekerFeedback) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.updateSeekerFeedback(applicationId, seekerFeedback));
    }



}
