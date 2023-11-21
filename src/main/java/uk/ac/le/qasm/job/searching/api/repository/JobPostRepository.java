package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import java.util.UUID;

public interface JobPostRepository extends JpaRepository<JobPost, UUID> {
    Page<JobPost> findByProvider(Provider provider, Pageable pageable);
}
