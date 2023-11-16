package uk.ac.le.qasm.job.searching.api.controller.Seeker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;
import uk.ac.le.qasm.job.searching.api.service.JobSeekerService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/auth/seekers")
public class JobSeekerACController {


    private final JobSeekerService jobSeekerService;

    public JobSeekerACController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSeeker(@RequestBody JobSeeker jobSeekerAccount){
        try {
            return ResponseEntity.ok(jobSeekerService.updateByUsername(jobSeekerAccount));
        }catch (BaseException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("message", ex.getDescription()));
        }
    }
    @PostMapping("/getbyuserame")
    public JobSeeker getByUsername(@RequestBody JobSeeker jobSeekerAccount){
        return jobSeekerService.findByUsername(jobSeekerAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") UUID id) {
        try{
            return ResponseEntity.ok(jobSeekerService.findByIdtest(id));
        }catch (BaseException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("message", ex.getDescription()));
        }
    }
}
