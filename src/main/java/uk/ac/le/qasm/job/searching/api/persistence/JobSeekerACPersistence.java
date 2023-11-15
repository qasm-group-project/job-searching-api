package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.repository.JobSeekerACRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class JobSeekerACPersistence {
    private final JobSeekerACRepository jobSeekerACRepository;


    public JobSeekerACPersistence(JobSeekerACRepository jobSeekerACRepository) {
        this.jobSeekerACRepository = jobSeekerACRepository;
    }

    public JobSeeker Create(JobSeeker jobSeekerAccount){
        return jobSeekerACRepository.save(jobSeekerAccount);
    }
    public Optional<JobSeeker> getById(UUID id){
        return  jobSeekerACRepository.findById(id);
    }

    public Boolean CheckUsernameIsPresent(String username){
        return (jobSeekerACRepository.findByUsername(username)).isPresent();
    }

}
