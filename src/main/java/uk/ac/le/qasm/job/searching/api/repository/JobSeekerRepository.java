package uk.ac.le.qasm.job.searching.api.repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;

import java.util.Optional;
import java.util.UUID;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, UUID> {
    Optional<JobSeeker> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE JobSeeker js set js.isVisible = :isVisible WHERE js = :jobSeeker")
    void updateJobSeekerIsVisible(JobSeeker jobSeeker, Boolean isVisible);
}
