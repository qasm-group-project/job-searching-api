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

import java.util.HashMap;
@Service
public class JSAuthServiceImpl implements JSAuthService {
    private final JobSeekerMapper jobSeekerMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;



    public JSAuthServiceImpl(JobSeekerMapper jobSeekerMapper, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jobSeekerMapper = jobSeekerMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Object> register(JobSeeker jobSeekerAccount) {
        HashMap<String,Object> res = new HashMap<>();
        QueryWrapper<JobSeeker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",jobSeekerAccount.getUsername());
        if (jobSeekerMapper.exists(queryWrapper)){
            res.put("message","username already exist!");
            res.put("status", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }else {
            if (jobSeekerMapper.insert(jobSeekerAccount)>=1){
//                var jobSeeker = JobSeeker.builder()
//                        .username(jobSeekerAccount.getUsername())
//                        .password(passwordEncoder.encode(jobSeekerAccount.getPassword()))
//                        .gender(jobSeekerAccount.getGender())
//                        .email(jobSeekerAccount.getEmail())
//                        .phone(jobSeekerAccount.getPhone())
//                        .firstname(jobSeekerAccount.getFirstname())
//                        .lastname(jobSeekerAccount.getLastname())
//                        .nickname(jobSeekerAccount.getNickname())
//                        .build();
//                var jwtToken = jwtService.generateToken(jobSeeker);
                res.put("message","register success!");
                res.put("status",HttpStatus.OK.value());
//                res.put("token",jwtToken);
            return new ResponseEntity<>(res, HttpStatus.OK);
            }else {
                res.put("message","system error");
                res.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    @Override
    public ResponseEntity<?> login(AuthenticationRequest authenticationRequest) {
        HashMap<String,Object> res = new HashMap<>();
        QueryWrapper<JobSeeker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",authenticationRequest.getUsername())
                    .eq("password",authenticationRequest.getPassword());

        var jobseeker = jobSeekerMapper.selectOne(queryWrapper);
        if (jobseeker!=null){
            res.put("message","Login success");
            res.put("status",HttpStatus.OK.value());
            res.put("token",jwtService.generateToken(jobseeker));
            return new ResponseEntity<>(res,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
