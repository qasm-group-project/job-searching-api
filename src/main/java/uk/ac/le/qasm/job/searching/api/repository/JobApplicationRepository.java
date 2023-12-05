package uk.ac.le.qasm.job.searching.api.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.enums.JobApplicationStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface JobApplicationRepository extends CrudRepository<JobApplication, UUID> {
    Set<JobApplication> findAllByJobPostId(UUID jobPostId);

    Set<JobApplication> findAllByApplicantId(UUID jobSeekerId);

    Optional<JobApplication> findByIdAndApplicantId(UUID jobApplicationId, UUID seekerId);

    Page<JobApplication> findByApplicantAndInterviewAfter(JobSeeker jobSeeker, LocalDateTime currentDateTime, Pageable pageable);

    @Query("SELECT ja FROM JobApplication ja WHERE ja.id = :jobApplicationId and ja.jobPost in (SELECT jp.id FROM JobPost jp where jp.provider = :provider)")
    Optional<JobApplication> findByIdAndProvider(UUID jobApplicationId, Provider provider);

    @Modifying
    @Transactional
    @Query("DELETE FROM JobApplication ja WHERE ja.id = :jobApplicationId AND ja.applicant = :jobSeeker")
    void deleteJobApplicationByIdAndApplicant(UUID jobApplicationId, JobSeeker jobSeeker);

    @Modifying
    @Transactional
    @Query("SELECT ja FROM JobApplication ja WHERE ja.jobPost in (SELECT jp.id FROM JobPost jp where jp.provider = :provider)")
    Set<JobApplication> findAllByProvider(Provider provider);

    @Modifying
    @Transactional
    @Query("UPDATE JobApplication set status = :jobApplicationStatus WHERE id = :jobApplicationId")
    void updateJobApplicationStatus(UUID jobApplicationId, JobApplicationStatus jobApplicationStatus);
}
