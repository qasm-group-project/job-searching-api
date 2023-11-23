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
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.entity.ProviderSocialMedia;
import uk.ac.le.qasm.job.searching.api.request.ProviderSocialMediaRequest;
import uk.ac.le.qasm.job.searching.api.request.ProviderSocialMediaRequestUpdate;
import uk.ac.le.qasm.job.searching.api.service.ProviderService;
import uk.ac.le.qasm.job.searching.api.service.ProviderSocialMediaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;
    private final ProviderSocialMediaService providerSocialMediaService;

    @GetMapping("/social-media")
    public ResponseEntity<Object> getAllSocialMediaPlatforms() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();
        List<ProviderSocialMedia> socialMediaPlatforms = providerSocialMediaService.getAllSocialMediaPlatforms(provider);
        Object responseBody = Map.of("data", socialMediaPlatforms);
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
}
