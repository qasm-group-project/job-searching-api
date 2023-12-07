package uk.ac.le.qasm.job.searching.api.adapter;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;

import java.util.UUID;

@Service
public interface JobSeekerService {
    JobSeeker findByUsername(String username);

    JobSeeker update(JobSeeker jobSeeker);

    JobSeeker findById(UUID id);

    void updateJobSeekerIsVisible(JobSeeker jobSeeker, Boolean isVisible);
}
