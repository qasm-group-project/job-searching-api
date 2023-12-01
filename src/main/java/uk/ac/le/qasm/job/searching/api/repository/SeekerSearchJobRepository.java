package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.enums.JobStatus;
import uk.ac.le.qasm.job.searching.api.enums.JobType;

import java.util.UUID;

public interface SeekerSearchJobRepository extends JpaRepository<JobPost, UUID> {
    Page<JobPost> findAllByIsVisibleAndStatus(Boolean isVisible,JobStatus jobStatus,Pageable pageable);
    Page<JobPost> findAllByCategoryAndJobTypeAndIsVisibleAndStatus(String category, JobType jobType, Boolean isVisible, JobStatus status, Pageable pageable);

    Page<JobPost> findAllByJobTypeAndIsVisibleAndStatus(JobType jobType,Boolean isVisible,JobStatus jobStatus, Pageable pageable);

    Page<JobPost> findAllByCategoryAndIsVisibleAndStatus(String category,Boolean isVisible,JobStatus jobStatus, Pageable pageable);

    Page<JobPost> findAllByTitleAndCategoryAndIsVisibleAndStatus(String title,String category,Boolean isVisible,JobStatus jobStatus, Pageable pageable);

    Page<JobPost> findAllByTitleAndJobTypeAndIsVisibleAndStatus(String title,JobType jobType,Boolean isVisible,JobStatus jobStatus, Pageable pageable);

    Page<JobPost> findAllByTitleAndIsVisibleAndStatus(String title,Boolean isVisible,JobStatus jobStatus, Pageable pageable);

    Page<JobPost> findAllByTitleAndJobTypeAndCategoryAndIsVisibleAndStatus(String title, JobType jobType, String category, Boolean isVisible, JobStatus jobStatus,Pageable pageable);

}
