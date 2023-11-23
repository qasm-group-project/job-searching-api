package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.entity.ProviderSocialMedia;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProviderSocialMediaRepository extends JpaRepository<ProviderSocialMedia, UUID>{
    List<ProviderSocialMedia> findAllByProvider(Provider provider);

    boolean existsByProviderAndPlatform(Provider provider, String platform);

    Optional<ProviderSocialMedia> findByIdAndProvider(UUID socialMediaId, Provider provider);
}
