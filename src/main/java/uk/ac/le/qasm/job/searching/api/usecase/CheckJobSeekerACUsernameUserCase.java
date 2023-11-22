package uk.ac.le.qasm.job.searching.api.usecase;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerPersistence;
@Component
public class CheckJobSeekerACUsernameUserCase {
    private final JobSeekerPersistence jobSeekerPersistence;

    public CheckJobSeekerACUsernameUserCase(JobSeekerPersistence jobSeekerPersistence) {
        this.jobSeekerPersistence = jobSeekerPersistence;
    }

    public Boolean Check(String username){
        return jobSeekerPersistence.CheckUsernameIsPresent(username);
    }
}
