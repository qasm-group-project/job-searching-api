package uk.ac.le.qasm.job.searching.api.usecase;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.UserNotFoundException;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerACPersistence;

import java.util.Optional;
import java.util.UUID;

@Component
public class GetJobSeekerACUserCase {
    private final JobSeekerACPersistence jobSeekerACPersistence;

    public GetJobSeekerACUserCase(JobSeekerACPersistence jobSeekerACPersistence) {
        this.jobSeekerACPersistence = jobSeekerACPersistence;
    }
    public JobSeeker getById(UUID uuid){
        Optional<JobSeeker> jobSeekerAccount = jobSeekerACPersistence.getById(uuid);
        return jobSeekerAccount.orElseThrow(UserNotFoundException::new);
    }
}
