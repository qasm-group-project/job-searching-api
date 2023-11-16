package uk.ac.le.qasm.job.searching.api.controller.Seeker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.request.JobApplicationRequest;
import uk.ac.le.qasm.job.searching.api.service.JobApplicationService;

@Slf4j
@RestController
@RequestMapping("api/v1/auth/application")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> createApplication(@RequestBody JobApplicationRequest jobApplication){

        return jobApplicationService.create(jobApplication);
    }

}
