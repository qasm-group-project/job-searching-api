package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerPersistence;

@Component
public class CreateJobSeekerService {
    private final JobSeekerPersistence jobSeekerPersistence;

    public CreateJobSeekerService(JobSeekerPersistence jobSeekerPersistence) {
        this.jobSeekerPersistence = jobSeekerPersistence;
    }

    public JobSeeker create(JobSeeker jobSeekerAccount){
        return jobSeekerPersistence.Create(jobSeekerAccount);
    }
}
