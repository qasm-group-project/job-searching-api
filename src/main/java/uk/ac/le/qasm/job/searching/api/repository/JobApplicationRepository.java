package uk.ac.le.qasm.job.searching.api.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface JobApplicationRepository extends CrudRepository<JobApplication, UUID> {
    Set<JobApplication> findAllByJobPostId(UUID jobPostId);

    Set<JobApplication> findAllByApplicantId(UUID jobSeekerId);

    Optional<JobApplication> findByIdAndApplicantId(UUID jobApplicationId, UUID seekerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM JobApplication ja WHERE ja.id = :jobApplicationId AND ja.applicant = :jobSeeker")
    void deleteJobApplicationByIdAndApplicant(UUID jobApplicationId, JobSeeker jobSeeker);

    @Modifying
    @Transactional
    @Query("SELECT ja FROM JobApplication ja INNER JOIN JobPost jp ON ja.jobPost = jp.id WHERE jp.provider = :providerId")
    Set<JobApplication> findAllByProviderId(UUID providerId);
}
