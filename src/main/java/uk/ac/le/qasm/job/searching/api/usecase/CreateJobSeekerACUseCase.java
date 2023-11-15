package uk.ac.le.qasm.job.searching.api.usecase;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerACPersistence;

@Component
public class CreateJobSeekerACUseCase {
    private final JobSeekerACPersistence jobSeekerACPersistence;

    public CreateJobSeekerACUseCase(JobSeekerACPersistence jobSeekerACPersistence) {
        this.jobSeekerACPersistence = jobSeekerACPersistence;
    }

    public JobSeeker create(JobSeeker jobSeekerAccount){
        return jobSeekerACPersistence.Create(jobSeekerAccount);
    }
}
