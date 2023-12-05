package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.enums.JobStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface JobPostRepository extends JpaRepository<JobPost, UUID> {
    Page<JobPost> findByProvider(Provider provider, Pageable pageable);

    Optional<JobPost> findByTitle(String title);

    Set<JobPost> findAllByIsVisibleAndStatus(boolean b, JobStatus jobStatus);

    Optional<JobPost> findByIdAndIsVisibleAndStatus(UUID jobId, boolean b, JobStatus jobStatus);

    Page<JobPost> findByProviderAndDeadlineBefore(Provider provider, LocalDateTime currentDateTime, Pageable pageable);

    Page<JobPost> findByDeadlineAfter(LocalDateTime currentDateTime, Pageable pageable);
}
