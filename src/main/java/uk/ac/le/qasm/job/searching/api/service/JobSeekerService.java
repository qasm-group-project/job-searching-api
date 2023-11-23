package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.config.JwtService;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.UserNotFoundException;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerPersistence;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JobSeekerService implements uk.ac.le.qasm.job.searching.api.adapter.JobSeekerService {

    private final JobSeekerPersistence jobSeekerPersistence;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JobSeekerService(JobSeekerPersistence jobSeekerPersistence, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jobSeekerPersistence = jobSeekerPersistence;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JobSeeker findByUsername(String username) {
        return jobSeekerPersistence.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public JobSeeker findById(UUID id) {
        return jobSeekerPersistence.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Map<String, Object> update(JobSeeker jobSeeker) {

        Map<String, Object> res = new HashMap<>();
        jobSeeker.setPassword(passwordEncoder.encode(jobSeeker.getPassword()));
        var update_res = jobSeekerPersistence.save(jobSeeker);
        var jwtToken = jwtService.generateToken(jobSeeker);
        res.put("message", "Update success!");
        res.put("status", HttpStatus.OK.value());
        res.put("token", jwtToken);
        res.put("Seeker", update_res);
        return res;
    }

}
