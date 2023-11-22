package uk.ac.le.qasm.job.searching.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import uk.ac.le.qasm.job.searching.api.mapper.JobSeekerMapper;
import uk.ac.le.qasm.job.searching.api.repository.SeekerRepository;
import uk.ac.le.qasm.job.searching.api.request.AuthenticationRequest;
import uk.ac.le.qasm.job.searching.api.service.JSAuthService;
import uk.ac.le.qasm.job.searching.api.usecase.CheckJobSeekerACUsernameUserCase;

import java.util.HashMap;
import java.util.Map;

@Service
public class JSAuthServiceImpl implements JSAuthService {
    private final JobSeekerMapper jobSeekerMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CheckJobSeekerACUsernameUserCase checkJobSeekerACUsernameUserCase;
    private final AuthenticationManager authenticationManager;
    private final SeekerRepository repository;

    public JSAuthServiceImpl(JobSeekerMapper jobSeekerMapper,
                             JwtService jwtService,
                             PasswordEncoder passwordEncoder,
                             CheckJobSeekerACUsernameUserCase checkJobSeekerACUsernameUserCase,
                             AuthenticationManager authenticationManager,
                             SeekerRepository repository) {
        this.jobSeekerMapper = jobSeekerMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.checkJobSeekerACUsernameUserCase = checkJobSeekerACUsernameUserCase;
        this.authenticationManager = authenticationManager;
        this.repository = repository;
    }

    @Override
    public Map<String, Object> register(JobSeeker jobSeekerAccount) {
        Map<String, Object> res = new HashMap<>();

        if (checkJobSeekerACUsernameUserCase.Check(jobSeekerAccount.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            var jobSeeker = JobSeeker.builder()
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
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword()
                    ));
            QueryWrapper<JobSeeker> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", authenticationRequest.getUsername());
            var jobseeker = jobSeekerMapper.selectOne(queryWrapper);
            var jwtToken = jwtService.generateToken(jobseeker);
            res.put("message", "Login success");
            res.put("token", jwtToken);

            return res;
        } catch (AuthenticationException e) {
            throw new UnauthorizedException();
        }

    }

}
