package uk.ac.le.qasm.job.searching.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.le.qasm.job.searching.api.adapter.JobSearchService;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/v1/seeker/job-posts")
public class JobSearchController {

    private final JobSearchService jobSearchService;

    public JobSearchController(JobSearchService jobSearchService) {
        this.jobSearchService = jobSearchService;
    }

    @GetMapping
    public ResponseEntity<Set<JobPost>> searchJob() {
        return ResponseEntity.ok(jobSearchService.searchAll());
    }

}
