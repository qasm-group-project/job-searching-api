package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
@Service
public interface JobSeekerService {
    int findByUsername(JobSeekerAccount jobSeekerAccount);
}
