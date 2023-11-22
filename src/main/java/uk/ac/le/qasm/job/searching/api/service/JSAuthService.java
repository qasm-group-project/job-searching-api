package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.config.JwtService;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.enums.Role;
import uk.ac.le.qasm.job.searching.api.exception.UnauthorizedException;
import uk.ac.le.qasm.job.searching.api.exception.UserAlreadyExistsException;
import uk.ac.le.qasm.job.searching.api.repository.SeekerRepository;
import uk.ac.le.qasm.job.searching.api.entity.AuthenticationRequest;
import uk.ac.le.qasm.job.searching.api.adapter.JobSeekerService;

import java.util.HashMap;
import java.util.Map;

@Service
public class JSAuthService implements uk.ac.le.qasm.job.searching.api.adapter.JSAuthService {
    private final JobSeekerService jobSeekerService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CheckJobSeekerUsernameService checkJobSeekerUsernameService;
    private final AuthenticationManager authenticationManager;
    private final SeekerRepository repository;

    public JSAuthService(JobSeekerService jobSeekerService,
                         JwtService jwtService,
                         PasswordEncoder passwordEncoder,
                         CheckJobSeekerUsernameService checkJobSeekerUsernameService,
                         AuthenticationManager authenticationManager,
                         SeekerRepository repository) {
        this.jobSeekerService = jobSeekerService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.checkJobSeekerUsernameService = checkJobSeekerUsernameService;
        this.authenticationManager = authenticationManager;
        this.repository = repository;
    }

    @Override
    public Map<String, Object> register(JobSeeker jobSeekerAccount) {
        Map<String, Object> res = new HashMap<>();

        if (checkJobSeekerUsernameService.Check(jobSeekerAccount.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            var
                    jobSeeker =
                    JobSeeker.builder()
                             .username(jobSeekerAccount.getUsername())
                             .password(passwordEncoder.encode(jobSeekerAccount.getPassword()))
                             .gender(jobSeekerAccount.getGender())
                             .email(jobSeekerAccount.getEmail())
                             .phone(jobSeekerAccount.getPhone())
                             .firstname(jobSeekerAccount.getFirstname())
                             .lastname(jobSeekerAccount.getLastname())
                             .nickname(jobSeekerAccount.getNickname())
                             .role(Role.PROVIDER)
                             .build();
            var jwtToken = jwtService.generateToken(jobSeeker);
            var user = repository.save(jobSeeker);
            res.put("message", "Login success!");
            res.put("status", HttpStatus.OK.value());
            res.put("token", jwtToken);
            res.put("user", user);
            return res;
        }
    }

    @Override
    public Map<String, Object> login(AuthenticationRequest authenticationRequest) throws AuthenticationException {
        Map<String, Object> res = new HashMap<>();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                                                                                       authenticationRequest.getPassword()));
            JobSeeker jobseeker = jobSeekerService.findByUsername(authenticationRequest.getUsername());
            var jwtToken = jwtService.generateToken(jobseeker);
            res.put("message", "Login success");
            res.put("token", jwtToken);

            return res;
        } catch (AuthenticationException e) {
            throw new UnauthorizedException();
        }

    }

}
