package uk.ac.le.qasm.job.searching.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerRequest;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;
import uk.ac.le.qasm.job.searching.api.adapter.JobSeekerService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/seeker")
public class SeekerController {


    private final JobSeekerService jobSeekerService;

    public SeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSeeker(@Valid @RequestBody JobSeekerRequest jobSeekerRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker authSeeker = (JobSeeker) authentication.getPrincipal();
        JobSeeker jobSeeker = JobSeeker.builder()
                .role(authSeeker.getRole())
                .password(authSeeker.getPassword())
                .nickname(jobSeekerRequest.getNickname())
                .lastname(jobSeekerRequest.getLastname())
                .firstname(jobSeekerRequest.getFirstname())
                .phone(jobSeekerRequest.getPhone())
                .email(jobSeekerRequest.getEmail())
                .username(authSeeker.getUsername())
                .id(authSeeker.getId())
                .gender(jobSeekerRequest.getGender())
                .build();
        return ResponseEntity.ok().body(jobSeekerService.update(jobSeeker));
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
