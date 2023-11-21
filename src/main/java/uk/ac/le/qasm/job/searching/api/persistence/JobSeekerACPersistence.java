package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.repository.JobSeekerRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class JobSeekerACPersistence {
    private final JobSeekerRepository jobSeekerRepository;


    public JobSeekerACPersistence(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    public JobSeeker Create(JobSeeker jobSeekerAccount){
        return jobSeekerRepository.save(jobSeekerAccount);
    }
    public Optional<JobSeeker> getById(UUID id){
        return  jobSeekerRepository.findById(id);
    }

    public Boolean CheckUsernameIsPresent(String username){
        return (jobSeekerRepository.findByUsername(username)).isPresent();
    }

}
