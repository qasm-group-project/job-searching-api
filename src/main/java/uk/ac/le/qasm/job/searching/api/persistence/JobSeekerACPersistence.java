package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
import uk.ac.le.qasm.job.searching.api.repository.JobSeekerACRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class JobSeekerACPersistence {
    private final JobSeekerACRepository jobSeekerACRepository;


    public JobSeekerACPersistence(JobSeekerACRepository jobSeekerACRepository) {
        this.jobSeekerACRepository = jobSeekerACRepository;
    }

    public JobSeekerAccount Create(JobSeekerAccount jobSeekerAccount){
        return jobSeekerACRepository.save(jobSeekerAccount);
    }
    public Optional<JobSeekerAccount> getById(UUID id){
        return  jobSeekerACRepository.findById(id);
    }

}
