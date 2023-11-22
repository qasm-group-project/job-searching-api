package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerPersistence;
@Component
public class CheckJobSeekerUsernameService {
    private final JobSeekerPersistence jobSeekerPersistence;

    public CheckJobSeekerUsernameService(JobSeekerPersistence jobSeekerPersistence) {
        this.jobSeekerPersistence = jobSeekerPersistence;
    }

    public Boolean Check(String username){
        return jobSeekerPersistence.CheckUsernameIsPresent(username);
    }
}
