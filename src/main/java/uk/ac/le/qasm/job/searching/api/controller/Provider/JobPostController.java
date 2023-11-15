package uk.ac.le.qasm.job.searching.api.controller.Provider;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.le.qasm.job.searching.api.Enumeration.JobType;
import uk.ac.le.qasm.job.searching.api.service.JobPostService;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.request.JobPostRequest;

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
                .provider(provider)
                .build();
        return jobPostService.saveJobPost(newJobPost);
    }
}
