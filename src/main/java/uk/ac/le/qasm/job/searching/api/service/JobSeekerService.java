package uk.ac.le.qasm.job.searching.api.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;

import java.util.UUID;

@Service
public interface JobSeekerService {
    JobSeeker findByUsername(JobSeeker jobSeekerAccount);

    int updateByUsername(JobSeeker jobSeekerAccount);

    JobSeeker findByIdtest(@Param("id")UUID id);
}
