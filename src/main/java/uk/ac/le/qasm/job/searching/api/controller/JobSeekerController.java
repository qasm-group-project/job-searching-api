package uk.ac.le.qasm.job.searching.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;
import uk.ac.le.qasm.job.searching.api.adapter.JobSeekerService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/seekers")
public class JobSeekerController {


    private final JobSeekerService jobSeekerService;

    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSeeker(@RequestBody JobSeeker jobSeeker) {
        return ResponseEntity.ok(jobSeekerService.update(jobSeeker));
    }

    @PostMapping("/getbyuserame")
    public JobSeeker getByUsername(@RequestBody JobSeeker jobSeekerAccount) {
        return jobSeekerService.findByUsername(jobSeekerAccount.getUsername());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(jobSeekerService.findById(id));
        } catch (BaseException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("message", ex.getDescription()));
        }
    }
}
