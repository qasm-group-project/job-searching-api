package uk.ac.le.qasm.job.searching.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;
import uk.ac.le.qasm.job.searching.api.request.AuthenticationRequest;
import uk.ac.le.qasm.job.searching.api.service.JSAuthService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/seeker")
public class JobSeekerAuthController {

    private final JSAuthService jsAuthService;

    public JobSeekerAuthController(JSAuthService jsAuthService) {
        this.jsAuthService = jsAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createSeeker(@Valid @RequestBody JobSeeker jobSeekerAccount, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errors));
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(jsAuthService.register(jobSeekerAccount));
        } catch (BaseException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("message", ex.getDescription()));
        }
    }

    @PostMapping( "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(jsAuthService.login(authenticationRequest));
    }
}
