package uk.ac.le.qasm.job.searching.api.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccountTest;

import java.util.UUID;

@Service
public interface JobSeekerService {
    JobSeekerAccount findByUsername(JobSeekerAccount jobSeekerAccount);

    int updateByUsername(JobSeekerAccount jobSeekerAccount);

    JobSeekerAccount findByIdtest(@Param("id")UUID id);
}
