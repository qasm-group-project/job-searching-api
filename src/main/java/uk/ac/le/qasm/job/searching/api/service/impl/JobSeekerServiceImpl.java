package uk.ac.le.qasm.job.searching.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
import uk.ac.le.qasm.job.searching.api.mapper.JobSeekerMapper;
import uk.ac.le.qasm.job.searching.api.service.JobSeekerService;
@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerMapper jobSeekerMapper;

    public JobSeekerServiceImpl(JobSeekerMapper jobSeekerMapper) {
        this.jobSeekerMapper = jobSeekerMapper;
    }

    @Override
    public int findByUsername(JobSeekerAccount jobSeekerAccount) {
        QueryWrapper<JobSeekerAccount> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",jobSeekerAccount.getUsername());
        return jobSeekerMapper.update(jobSeekerAccount,queryWrapper);
    }
}
