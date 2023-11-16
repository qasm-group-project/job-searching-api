package uk.ac.le.qasm.job.searching.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.le.qasm.job.searching.api.service.JobSeachService;

@Slf4j
@RestController
//change the RequestMapping later!
@RequestMapping("/api/v1/auth/search")
public class JobSearchController {

    private final JobSeachService jobSeachService;

    public JobSearchController(JobSeachService jobSeachService) {
        this.jobSeachService = jobSeachService;
    }

    @GetMapping
    public ResponseEntity<?> searchJob(){
        return jobSeachService.searchAll();
    }

}
