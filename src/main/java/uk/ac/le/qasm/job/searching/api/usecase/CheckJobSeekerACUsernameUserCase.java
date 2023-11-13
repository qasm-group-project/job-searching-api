package uk.ac.le.qasm.job.searching.api.usecase;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerACPersistence;
@Component
public class CheckJobSeekerACUsernameUserCase {
    private final JobSeekerACPersistence jobSeekerACPersistence;

    public CheckJobSeekerACUsernameUserCase(JobSeekerACPersistence jobSeekerACPersistence) {
        this.jobSeekerACPersistence = jobSeekerACPersistence;
    }

    public Boolean Check(String username){
        return jobSeekerACPersistence.CheckUsernameIsPresent(username);
    }
}
