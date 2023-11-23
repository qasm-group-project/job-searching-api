package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.repository.JobSeekerRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class JobSeekerPersistence {
    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerPersistence(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    public JobSeeker save(JobSeeker jobSeekerAccount){
        return jobSeekerRepository.save(jobSeekerAccount);
    }

    public Boolean checkUsernameIsPresent(String username){
        return (jobSeekerRepository.findByUsername(username)).isPresent();
    }

    public Optional<JobSeeker> findByUsername(String username) {
        return this.jobSeekerRepository.findByUsername(username);
    }


    public Optional<JobSeeker> findById(UUID id) {
        return jobSeekerRepository.findById(id);
    }
}
