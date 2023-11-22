package uk.ac.le.qasm.job.searching.api.adapter;

import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.AuthenticationRequest;

import java.util.Map;


public interface JSAuthService {
    Map<String, Object> register(JobSeeker jobSeekerAccount);
    Map<String, Object> login(AuthenticationRequest authenticationRequest);
}
