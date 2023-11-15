package uk.ac.le.qasm.job.searching.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.config.JwtService;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.mapper.JobSeekerMapper;
import uk.ac.le.qasm.job.searching.api.request.AuthenticationRequest;
import uk.ac.le.qasm.job.searching.api.service.JSAuthService;
import uk.ac.le.qasm.job.searching.api.usecase.CheckJobSeekerACUsernameUserCase;
import uk.ac.le.qasm.job.searching.api.usecase.CreateJobSeekerACUseCase;

import java.util.HashMap;
@Service
public class JSAuthServiceImpl implements JSAuthService {
    private final JobSeekerMapper jobSeekerMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CreateJobSeekerACUseCase createJobSeekerACUseCase;
    private final CheckJobSeekerACUsernameUserCase checkJobSeekerACUsernameUserCase;
    public JSAuthServiceImpl(JobSeekerMapper jobSeekerMapper, JwtService jwtService, PasswordEncoder passwordEncoder, CreateJobSeekerACUseCase createJobSeekerACUseCase, CheckJobSeekerACUsernameUserCase checkJobSeekerACUsernameUserCase) {
        this.jobSeekerMapper = jobSeekerMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.createJobSeekerACUseCase = createJobSeekerACUseCase;
        this.checkJobSeekerACUsernameUserCase = checkJobSeekerACUsernameUserCase;
    }

    @Override
    public ResponseEntity<Object> register(JobSeeker jobSeekerAccount) {
        HashMap<String,Object> res = new HashMap<>();

        if (checkJobSeekerACUsernameUserCase.Check(jobSeekerAccount.getUsername())){
            res.put("message","username already exist!");
            res.put("status", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }else {
                var jobSeeker = JobSeeker.builder()
                        .username(jobSeekerAccount.getUsername())
                        .password(passwordEncoder.encode(jobSeekerAccount.getPassword()))
                        .gender(jobSeekerAccount.getGender())
                        .email(jobSeekerAccount.getEmail())
                        .phone(jobSeekerAccount.getPhone())
                        .firstname(jobSeekerAccount.getFirstname())
                        .lastname(jobSeekerAccount.getLastname())
                        .nickname(jobSeekerAccount.getNickname())
                        .build();
//                var jwtToken = jwtService.generateToken(jobSeeker);
//                res.put("token",jwtToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(createJobSeekerACUseCase.create(jobSeeker));
        }
    }

    @Override
    public ResponseEntity<?> login(AuthenticationRequest authenticationRequest) {
        HashMap<String,Object> res = new HashMap<>();
        QueryWrapper<JobSeeker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",authenticationRequest.getUsername());

        var jobseeker = jobSeekerMapper.selectOne(queryWrapper);
        if (jobseeker!=null){
            if (passwordEncoder.matches(authenticationRequest.getPassword(),jobseeker.getPassword())){
                res.put("message","Login success");
                res.put("status",HttpStatus.OK.value());
                res.put("token",jwtService.generateToken(jobseeker));
                return new ResponseEntity<>(res,HttpStatus.OK);
            }else {
                res.put("message","wrong password");
                res.put("status",HttpStatus.UNAUTHORIZED.value());
                return new ResponseEntity<>(res,HttpStatus.UNAUTHORIZED);
            }
        }else {
            res.put("message","username does not exist");
            res.put("status",HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
        }

    }
}
