package uk.ac.le.qasm.job.searching.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import java.util.Optional;
import java.util.UUID;

public interface JobSeekerACRepository extends JpaRepository<JobSeeker, UUID> {
    Optional<JobSeeker> findByUsername(String username);
}
