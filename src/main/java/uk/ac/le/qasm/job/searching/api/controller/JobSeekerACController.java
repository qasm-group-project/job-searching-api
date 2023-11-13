package uk.ac.le.qasm.job.searching.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;
import uk.ac.le.qasm.job.searching.api.usecase.CreateJobSeekerACUseCase;
import uk.ac.le.qasm.job.searching.api.usecase.GetJobSeekerACUserCase;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/seekers")
public class JobSeekerACController {
    private final CreateJobSeekerACUseCase createJobSeekerACUseCase;
    private final GetJobSeekerACUserCase getJobSeekerACUserCase;

    public JobSeekerACController(CreateJobSeekerACUseCase createJobSeekerACUseCase, GetJobSeekerACUserCase getJobSeekerACUserCase) {
        this.createJobSeekerACUseCase = createJobSeekerACUseCase;
        this.getJobSeekerACUserCase = getJobSeekerACUserCase;
    }

    @PostMapping
    public ResponseEntity<?> createSeeker(@RequestBody JobSeekerAccount jobSeekerAccount) {
        try {

        return ResponseEntity.status(HttpStatus.CREATED).body(createJobSeekerACUseCase.create(jobSeekerAccount));
        } catch (BaseException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("message", ex.getDescription()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(getJobSeekerACUserCase.getById(id));
        } catch (BaseException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("message", ex.getDescription()));
        }
    }


}
