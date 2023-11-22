package uk.ac.le.qasm.job.searching.api.usecase;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerPersistence;

@Component
public class CreateJobSeekerACUseCase {
    private final JobSeekerPersistence jobSeekerPersistence;

    public CreateJobSeekerACUseCase(JobSeekerPersistence jobSeekerPersistence) {
        this.jobSeekerPersistence = jobSeekerPersistence;
    }

    public JobSeeker create(JobSeeker jobSeekerAccount){
        return jobSeekerPersistence.Create(jobSeekerAccount);
    }
}
