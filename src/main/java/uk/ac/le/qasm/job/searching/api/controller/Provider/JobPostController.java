package uk.ac.le.qasm.job.searching.api.controller.Provider;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.Enumeration.JobType;
import uk.ac.le.qasm.job.searching.api.Service.JobPostService;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.request.JobPostRequest;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provider/job-post")
@RequiredArgsConstructor
public class JobPostController {
    private final JobPostService jobPostService;
    @PostMapping("/create")
    public ResponseEntity<Object> createJobPost(@Valid @RequestBody JobPostRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();
        JobPost newJobPost = JobPost.builder()
                .title(request.getTitle())
                .jobType(JobType.valueOf(request.getJobType()))
                .description(request.getDescription())
                .salary(request.getSalary())
                .isVisible(request.getIsVisible())
                .provider(provider)
                .build();
        return jobPostService.saveJobPost(newJobPost);
    }
    @GetMapping("")
    public ResponseEntity<Page<JobPost>> getAllJobPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<JobPost> jobPosts = jobPostService.getAllJobPosts(PageRequest.of(page, size));
            return new ResponseEntity<>(jobPosts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{jobPostId}")
    public ResponseEntity<JobPost> updateJobPost(
            @PathVariable UUID jobPostId,
            @RequestBody JobPostRequest jobPostRequest
    ) {
        try {
            JobPost updatedPost = jobPostService.updateJobPost(jobPostId, jobPostRequest);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{jobPostId}")
    public ResponseEntity<Object> deleteJobPost(@PathVariable UUID jobPostId) {
        return jobPostService.deleteJobPost(jobPostId);
    }
}
