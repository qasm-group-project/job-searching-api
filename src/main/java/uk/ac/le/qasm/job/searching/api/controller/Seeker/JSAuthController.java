package uk.ac.le.qasm.job.searching.api.controller.Seeker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;
import uk.ac.le.qasm.job.searching.api.request.AuthenticationRequest;
import uk.ac.le.qasm.job.searching.api.service.JSAuthService;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/v1/auth/seeker")
public class JSAuthController {

    private final JSAuthService jsAuthService;

    public JSAuthController(JSAuthService jsAuthService) {
        this.jsAuthService = jsAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createSeeker(@RequestBody JobSeeker jobSeekerAccount) {
        try {
            return jsAuthService.register(jobSeekerAccount);
        } catch (BaseException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("message", ex.getDescription()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        return jsAuthService.login(authenticationRequest);
    }
}
