package uk.ac.le.qasm.job.searching.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
import uk.ac.le.qasm.job.searching.api.mapper.JobSeekerMapper;
import uk.ac.le.qasm.job.searching.api.service.JobSeekerService;

import java.util.UUID;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerMapper jobSeekerMapper;

    public JobSeekerServiceImpl(JobSeekerMapper jobSeekerMapper) {
        this.jobSeekerMapper = jobSeekerMapper;
    }

    @Override
    public JobSeekerAccount findByUsername(JobSeekerAccount jobSeekerAccount) {
        QueryWrapper<JobSeekerAccount> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",jobSeekerAccount.getUsername());
        return jobSeekerMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateByUsername(JobSeekerAccount jobSeekerAccount) {
        QueryWrapper<JobSeekerAccount> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",jobSeekerAccount.getUsername());
        return jobSeekerMapper.update(jobSeekerAccount,queryWrapper);
    }



    @Override
    public JobSeekerAccount findByIdtest(@Param("id") UUID id) {
        LambdaQueryWrapper<JobSeekerAccount> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(JobSeekerAccount::getId,id);
        return jobSeekerMapper.selectOne(lambdaQueryWrapper);
    }


}
