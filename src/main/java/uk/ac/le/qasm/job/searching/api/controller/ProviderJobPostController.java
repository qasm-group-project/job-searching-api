package uk.ac.le.qasm.job.searching.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.enums.JobType;
import uk.ac.le.qasm.job.searching.api.service.JobPostService;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.entity.JobPostRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provider/job-post")
@RequiredArgsConstructor
public class ProviderJobPostController {
    private final JobPostService jobPostService;

    @PostMapping("/create")
    public ResponseEntity<Object> createJobPost(@Valid @RequestBody JobPostRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();
        JobPost
                newJobPost =
                JobPost.builder()
                       .title(request.getTitle())
                       .jobType(JobType.valueOf(request.getJobType()))
                       .description(request.getDescription())
                       .salary(request.getSalary())
                       .isVisible(request.getIsVisible())
                       .status(request.getJobStatus())
                       .deadline(request.getDeadline())
                       .provider(provider)
                       .build();
        JobPost jobPost = jobPostService.saveJobPost(newJobPost);
        Object responseBody = Map.of("message", "Job Post Created successfully!", "id", jobPost.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @GetMapping("")
    public ResponseEntity<Page<JobPost>> getAllJobPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Provider provider = (Provider) authentication.getPrincipal();
            Page<JobPost> jobPosts = jobPostService.getAllJobPostsByProvider(provider, PageRequest.of(page, size));
            return new ResponseEntity<>(jobPosts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{jobPostId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateJobPost(@PathVariable UUID jobPostId, @Valid @RequestBody JobPostRequest jobPostRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Provider provider = (Provider) authentication.getPrincipal();
            JobPost updatedPost = jobPostService.updateJobPost(provider, jobPostId, jobPostRequest);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (Exception e) {
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }

    @DeleteMapping("/{jobPostId}")
    public ResponseEntity<Object> deleteJobPost(@PathVariable UUID jobPostId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Provider provider = (Provider) authentication.getPrincipal();
            jobPostService.deleteJobPost(provider, jobPostId);
            Object responseBody = Map.of("message", "Job Post Deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }

    @GetMapping("/expired")
    public ResponseEntity<Page<JobPost>> getExpiredJobPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Provider provider = (Provider) authentication.getPrincipal();

            LocalDateTime currentDateTime = LocalDateTime.now();
            Page<JobPost> expiredJobPosts = jobPostService.getExpiredJobPosts(provider, currentDateTime, PageRequest.of(page, size));

            return new ResponseEntity<>(expiredJobPosts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
