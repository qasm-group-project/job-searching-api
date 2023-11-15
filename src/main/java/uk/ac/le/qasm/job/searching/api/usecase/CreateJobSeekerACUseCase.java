package uk.ac.le.qasm.job.searching.api.usecase;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerACPersistence;

@Component
public class CreateJobSeekerACUseCase {
    private final JobSeekerACPersistence jobSeekerACPersistence;

    public CreateJobSeekerACUseCase(JobSeekerACPersistence jobSeekerACPersistence) {
        this.jobSeekerACPersistence = jobSeekerACPersistence;
    }

    public JobSeekerAccount create(JobSeekerAccount jobSeekerAccount){
        return jobSeekerACPersistence.Create(jobSeekerAccount);
    }
}
