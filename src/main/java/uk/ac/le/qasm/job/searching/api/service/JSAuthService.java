package uk.ac.le.qasm.job.searching.api.service;

import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.request.AuthenticationRequest;

import java.util.Map;


public interface JSAuthService {
    Map<String, Object> register(JobSeeker jobSeekerAccount);
    Map<String, Object> login(AuthenticationRequest authenticationRequest);
}
