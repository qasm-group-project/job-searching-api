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

    public JobSeeker Create(JobSeeker jobSeekerAccount){
        return jobSeekerRepository.save(jobSeekerAccount);
    }

    public Boolean CheckUsernameIsPresent(String username){
        return (jobSeekerRepository.findByUsername(username)).isPresent();
    }

    public Optional<JobSeeker> findByUsername(String username) {
        return this.jobSeekerRepository.findByUsername(username);
    }

    public JobSeeker update(JobSeeker jobSeeker) {
        return this.jobSeekerRepository.save(jobSeeker);
    }

    public Optional<JobSeeker> findById(UUID id) {
        return jobSeekerRepository.findById(id);
    }

    public void updateJobSeekerIsVisible(JobSeeker jobSeeker, Boolean isVisible) {
        this.jobSeekerRepository.updateJobSeekerIsVisible(jobSeeker, isVisible);
    }
}
