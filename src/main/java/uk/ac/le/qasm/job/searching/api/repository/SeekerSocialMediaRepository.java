package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.SeekerSocialMedia;

import java.util.Optional;
import java.util.UUID;

public interface SeekerSocialMediaRepository extends JpaRepository<SeekerSocialMedia, UUID> {

    boolean existsBySeekerAndPlatform(JobSeeker seeker, String platform);

    Optional<SeekerSocialMedia> findByIdAndSeeker(UUID socialMediaId, JobSeeker seeker);
}
