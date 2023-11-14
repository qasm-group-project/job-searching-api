package uk.ac.le.qasm.job.searching.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccountTest;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;
import uk.ac.le.qasm.job.searching.api.service.JobSeekerService;
import uk.ac.le.qasm.job.searching.api.usecase.CheckJobSeekerACUsernameUserCase;
import uk.ac.le.qasm.job.searching.api.usecase.CreateJobSeekerACUseCase;
import uk.ac.le.qasm.job.searching.api.usecase.GetJobSeekerACUserCase;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/seekers")
public class JobSeekerACController {
    private final CreateJobSeekerACUseCase createJobSeekerACUseCase;
    private final GetJobSeekerACUserCase getJobSeekerACUserCase;
    private final CheckJobSeekerACUsernameUserCase checkJobSeekerACUsernameUserCase;
    private final JobSeekerService jobSeekerService;


    public JobSeekerACController(CreateJobSeekerACUseCase createJobSeekerACUseCase, GetJobSeekerACUserCase getJobSeekerACUserCase, CheckJobSeekerACUsernameUserCase checkJobSeekerACUsernameUserCase, JobSeekerService jobSeekerService) {
        this.createJobSeekerACUseCase = createJobSeekerACUseCase;
        this.getJobSeekerACUserCase = getJobSeekerACUserCase;
        this.checkJobSeekerACUsernameUserCase = checkJobSeekerACUsernameUserCase;
        this.jobSeekerService = jobSeekerService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSeeker(@RequestBody JobSeekerAccount jobSeekerAccount) {
        try {
            if (!checkJobSeekerACUsernameUserCase.Check(jobSeekerAccount.getUsername())){
                return ResponseEntity.status(HttpStatus.CREATED).body(createJobSeekerACUseCase.create(jobSeekerAccount));
            }else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message","username already exist!"));
            }
        } catch (BaseException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("message", ex.getDescription()));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSeeker(@RequestBody JobSeekerAccount jobSeekerAccount){
        try {
            return ResponseEntity.ok(jobSeekerService.updateByUsername(jobSeekerAccount));
        }catch (BaseException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("message", ex.getDescription()));
        }
    }
    @PostMapping("/getbyuserame")
    public JobSeekerAccount getByUsername(@RequestBody JobSeekerAccount jobSeekerAccount){
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
