package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.SeekerSavedJobPost;

import java.util.Optional;
import java.util.UUID;

public interface SavedJobPostRepository extends JpaRepository<SeekerSavedJobPost, UUID> {
    Optional<SeekerSavedJobPost> findByJobSeekerAndJobPost(JobSeeker jobSeeker, JobPost jobPost);

    Page<SeekerSavedJobPost> findByJobSeeker(JobSeeker jobSeeker, Pageable pageable);
}
