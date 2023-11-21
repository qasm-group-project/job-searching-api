package uk.ac.le.qasm.job.searching.api.controller.Provider;


import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.le.qasm.job.searching.api.Enumeration.JobType;
import uk.ac.le.qasm.job.searching.api.Service.JobPostService;
import uk.ac.le.qasm.job.searching.api.config.JwtService;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.request.JobPostRequest;

import java.security.SignatureException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/provider/job-post")
@RequiredArgsConstructor
public class JobPostController {
    private final JobPostService jobPostService;
    @PostMapping("/create")
    public ResponseEntity<Object> createJobPost(@Valid @RequestBody JobPostRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider provider = (Provider) authentication.getPrincipal();
        JobPost newJobPost = JobPost.builder()
                .title(request.getTitle())
                .jobType(JobType.valueOf(request.getJobType()))
                .description(request.getDescription())
                .salary(request.getSalary())
                .provider(provider)
                .build();
        jobPostService.saveJobPost(newJobPost);
        Object responseBody = Map.of("message", "Job Post Created successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

    }
}
