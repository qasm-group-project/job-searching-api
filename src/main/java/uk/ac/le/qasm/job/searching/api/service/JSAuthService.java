package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.request.AuthenticationRequest;


public interface JSAuthService {
    ResponseEntity<?> register(JobSeeker jobSeekerAccount);
    ResponseEntity<?> login(AuthenticationRequest authenticationRequest);
}
