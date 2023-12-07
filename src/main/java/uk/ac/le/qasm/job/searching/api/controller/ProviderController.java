package uk.ac.le.qasm.job.searching.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.entity.*;
import uk.ac.le.qasm.job.searching.api.enums.JobApplicationStatus;
import uk.ac.le.qasm.job.searching.api.enums.JobType;
import uk.ac.le.qasm.job.searching.api.persistence.JobApplicationPersistence;
import uk.ac.le.qasm.job.searching.api.request.JobApplicationInterviewRequestUpdate;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.entity.ProviderNews;
import uk.ac.le.qasm.job.searching.api.entity.ProviderSocialMedia;
import uk.ac.le.qasm.job.searching.api.request.ProviderNewsRequest;
import uk.ac.le.qasm.job.searching.api.request.ProviderSocialMediaRequest;
import uk.ac.le.qasm.job.searching.api.request.ProviderSocialMediaRequestUpdate;
import uk.ac.le.qasm.job.searching.api.service.ProviderNewsService;
import uk.ac.le.qasm.job.searching.api.service.ProviderSocialMediaService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderSocialMediaService providerSocialMediaService;
    private final JobApplicationPersistence jobApplicationPersistence;
    private final ProviderNewsService providerNewsService;

    @GetMapping("/social-media")
    public ResponseEntity<Object> getAllSocialMediaPlatforms() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();
        List<ProviderSocialMedia> socialMediaPlatforms = providerSocialMediaService.getAllSocialMediaPlatforms(provider);
        Object responseBody = Map.of("data", socialMediaPlatforms,
                "number_of_social_media_platforms", socialMediaPlatforms.size());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/social-media")
    public ResponseEntity<Object> addSocialMediaPlatform(
           @Valid @RequestBody ProviderSocialMediaRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Provider provider = (Provider) authentication.getPrincipal();
            var newSocialPlatform = ProviderSocialMedia.builder()
                    .platform(request.getPlatform())
                    .link(request.getLink())
                    .provider(provider)
                    .build();
            ProviderSocialMedia providerSocialMedia = providerSocialMediaService.saveProviderSocialMedia(newSocialPlatform);
            Object responseBody = Map.of("message", "Provider Social Media Created successfully!", "id",
                    providerSocialMedia.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        }catch (Exception e){
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }

    @PutMapping("/social-media/{socialMediaId}")
    public ResponseEntity<Object> updateSocialMediaPlatform(
            @PathVariable UUID socialMediaId,
            @Valid @RequestBody ProviderSocialMediaRequestUpdate request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();

        var updatedSocialMedia = ProviderSocialMedia.builder()
                .link(request.getLink())
                .provider(provider)
                .build();
        try {
            ProviderSocialMedia result = providerSocialMediaService.updateProviderSocialMedia(socialMediaId, updatedSocialMedia);
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
        Provider provider = (Provider) authentication.getPrincipal();
        try {
            providerSocialMediaService.deleteProviderSocialMedia(socialMediaId, provider);
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
        Provider provider = (Provider) authentication.getPrincipal();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(jobApplicationPersistence.findAllByProvider(provider));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message",e.getMessage()));
        }
    }

    @PutMapping("/job-applications/{job_application_id}/accept")
    public ResponseEntity<Object> acceptJobApplication(@PathVariable("job_application_id") UUID jobApplicationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();

        try {
            jobApplicationPersistence.updateJobApplicationStatus(jobApplicationId, provider, JobApplicationStatus.ACCEPTED);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Job Application accept successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message",e.getMessage()));
        }
    }

    @PutMapping("/job-applications/{job_application_id}/deny")
    public ResponseEntity<Object> denyJobApplication(@PathVariable("job_application_id") UUID jobApplicationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();

        try {
            jobApplicationPersistence.updateJobApplicationStatus(jobApplicationId, provider, JobApplicationStatus.DENIED);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Job Application deny successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message",e.getMessage()));
        }
    }

    @PutMapping("/job-applications/{job_application_id}/interview")
    public ResponseEntity<Object> updateJobApplicationInterview(@PathVariable("job_application_id") UUID jobApplicationId, @Valid @RequestBody JobApplicationInterviewRequestUpdate request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Provider provider = (Provider) authentication.getPrincipal();
            var updateJobApplication = JobApplication.builder()
                    .interview(request.getInterview())
                    .build();
        try {
            JobApplication result = jobApplicationPersistence.updateJobApplication(jobApplicationId, provider, updateJobApplication);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Job Application updated interview successfully", "id", result.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message",e.getMessage()));
        }
    }
    // provider news

    @PostMapping("/news")
    public ResponseEntity<Object> addProviderNews(
            @Valid @RequestBody ProviderNewsRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();
        var newProviderNews = ProviderNews.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .provider(provider)
                .build();
        ProviderNews providerNews = providerNewsService.saveProviderNews(newProviderNews);
        Object responseBody = Map.of("message", "Provider News Created successfully!", "id",
                providerNews.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping("/news/{newsId}")
    public ResponseEntity<Object> updateProviderNews(
            @PathVariable UUID newsId,
            @Valid @RequestBody ProviderNewsRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();

        var updatedProviderNews = ProviderNews.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .provider(provider)
                .build();
        try {
            ProviderNews result = providerNewsService.updateProviderNews(newsId, updatedProviderNews);
            Object responseBody = Map.of("message", "Provider News updated successfully", "id", result.getId());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }

    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<Object> deleteProviderNews(@PathVariable UUID newsId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();
        try {
            providerNewsService.deleteProviderNews(newsId, provider);
            Object responseBody = Map.of("message", "Provider News deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            Object responseBody = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }
}
