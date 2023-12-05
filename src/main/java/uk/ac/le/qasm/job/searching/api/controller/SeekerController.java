package uk.ac.le.qasm.job.searching.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.entity.*;
import uk.ac.le.qasm.job.searching.api.enums.JobApplicationStatus;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;
import uk.ac.le.qasm.job.searching.api.adapter.JobSeekerService;
import uk.ac.le.qasm.job.searching.api.persistence.JobApplicationPersistence;
import uk.ac.le.qasm.job.searching.api.request.SeekerSocialMediaRequest;
import uk.ac.le.qasm.job.searching.api.request.SeekerSocialMediaRequestUpdate;
import uk.ac.le.qasm.job.searching.api.service.SeekerSocialMediaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/seeker/")
public class SeekerController {
    private final SeekerSocialMediaService seekerSocialMediaService;

    private final JobSeekerService jobSeekerService;
    private final JobApplicationPersistence jobApplicationPersistence;

    public SeekerController(SeekerSocialMediaService seekerSocialMediaService, JobSeekerService jobSeekerService, JobApplicationPersistence jobApplicationPersistence) {
        this.seekerSocialMediaService = seekerSocialMediaService;
        this.jobSeekerService = jobSeekerService;
        this.jobApplicationPersistence = jobApplicationPersistence;
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

    @GetMapping("/social-media")
    public ResponseEntity<Object> getAllSocialMediaPlatforms() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker seeker = (JobSeeker) authentication.getPrincipal();
        List<SeekerSocialMedia> socialMediaPlatforms = seekerSocialMediaService.getAllSocialMediaPlatforms(seeker);
        Object responseBody = Map.of("data", socialMediaPlatforms);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/social-media")
    public ResponseEntity<Object> addSocialMediaPlatform(
            @Valid @RequestBody SeekerSocialMediaRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();
            System.out.println(jobSeeker.getUsername());
            var newSocialPlatform = SeekerSocialMedia.builder()
                    .platform(request.getPlatform())
                    .link(request.getLink())
                    .seeker(jobSeeker)
                    .build();
            SeekerSocialMedia seekerSocialMedia = seekerSocialMediaService.saveSeekerSocialMedia(newSocialPlatform);
            Object responseBody = Map.of("message", "Seeker Social Media Created successfully!", "id",
                    seekerSocialMedia.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        }catch (Exception e){
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }

    @PutMapping("/social-media/{socialMediaId}")
    public ResponseEntity<Object> updateSocialMediaPlatform(
            @PathVariable UUID socialMediaId,
            @Valid @RequestBody SeekerSocialMediaRequestUpdate request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();

        var updatedSocialMedia = SeekerSocialMedia.builder()
                .link(request.getLink())
                .seeker(jobSeeker)
                .build();
        try {
            SeekerSocialMedia result = seekerSocialMediaService.updateSeekerSocialMedia(socialMediaId, updatedSocialMedia);
            Object responseBody = Map.of("message", "Social Media Platform updated successfully", "id", result.getId());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }
    @DeleteMapping("/social-media/{socialMediaId}")
    public ResponseEntity<Object> deleteSocialMediaPlatform(@PathVariable UUID socialMediaId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();
        try {
            seekerSocialMediaService.deleteSeekerSocialMedia(socialMediaId, jobSeeker);
            Object responseBody = Map.of("message", "Social Media Platform deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }
    @GetMapping("/job-applications")
    public ResponseEntity<Object> searchMyApplications(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(jobApplicationPersistence.findAllByApplicantId(jobSeeker.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message",e.getMessage()));
        }
    }

    @DeleteMapping("/job-applications/{job_application_id}")
    public ResponseEntity<Object> deleteMyApplication(@PathVariable("job_application_id") UUID jobApplicationId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();
        try {
            jobApplicationPersistence.deleteJobApplication(jobApplicationId, jobSeeker);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Job Application deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message",e.getMessage()));
        }
    }

    @PutMapping("/is-visible/visible")
    public ResponseEntity<Object> visibleSeeker() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();

        try {
            jobSeekerService.updateJobSeekerIsVisible(jobSeeker, true);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Job Seeker is visible"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message",e.getMessage()));
        }
    }

    @PutMapping("/is-visible/invisible")
    public ResponseEntity<Object> invisibleSeeker() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();

        try {
            jobSeekerService.updateJobSeekerIsVisible(jobSeeker, false);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Job Seeker is invisible"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message",e.getMessage()));
        }
    }

    @GetMapping("/job-applications/interviews")
    public ResponseEntity<Page<JobApplication>> getInterviewsJobApplications(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JobSeeker jobSeeker = (JobSeeker) authentication.getPrincipal();
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            Page<JobApplication> interviewsJobApplications = jobApplicationPersistence.getInterviewsJobApplications(jobSeeker, currentDateTime, PageRequest.of(page, size, Sort.by("interview")));

            return new ResponseEntity<>(interviewsJobApplications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
