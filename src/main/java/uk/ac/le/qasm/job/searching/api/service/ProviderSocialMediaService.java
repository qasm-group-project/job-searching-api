package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.entity.ProviderSocialMedia;
import uk.ac.le.qasm.job.searching.api.repository.ProviderSocialMediaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProviderSocialMediaService {
    private final ProviderSocialMediaRepository repo;

    @Autowired
    public ProviderSocialMediaService(ProviderSocialMediaRepository providerSocialMediaRepository) {
        this.repo = providerSocialMediaRepository;
    }

    public ProviderSocialMedia saveProviderSocialMedia(ProviderSocialMedia providerSocialMedia) {
        if (isPlatformAlreadyExistsForProvider(providerSocialMedia.getProvider(), providerSocialMedia.getPlatform())) {
            throw new RuntimeException("Platform already exists for this provider");
        }
        return repo.save(providerSocialMedia);
    }
    private boolean isPlatformAlreadyExistsForProvider(Provider provider, String platform) {
        return repo.existsByProviderAndPlatform(provider, platform);
    }

    public List<ProviderSocialMedia> getAllSocialMediaPlatforms(Provider provider) {
        return repo.findAllByProvider(provider);
    }

    public ProviderSocialMedia updateProviderSocialMedia(UUID socialMediaId, ProviderSocialMedia updatedSocialMedia) {
        Optional<ProviderSocialMedia> existingSocialMediaOptional = repo.findByIdAndProvider(
                socialMediaId, updatedSocialMedia.getProvider());

        if (existingSocialMediaOptional.isPresent()) {
            ProviderSocialMedia existingSocialMedia = existingSocialMediaOptional.get();
            // Update fields as needed
            existingSocialMedia.setPlatform(existingSocialMedia.getPlatform());
            existingSocialMedia.setLink(updatedSocialMedia.getLink());
            // Save the updated social media platform
            return repo.save(existingSocialMedia);
        } else {
            // Handle the case where the social media platform with the given ID and provider is not found
            throw new RuntimeException("Social media platform not found for the given ID and provider");
        }
    }

    public void deleteProviderSocialMedia(UUID socialMediaId, Provider provider) {
        Optional<ProviderSocialMedia> existingSocialMediaOptional = repo.findByIdAndProvider(
                socialMediaId, provider);

        if (existingSocialMediaOptional.isPresent()) {
            ProviderSocialMedia existingSocialMedia = existingSocialMediaOptional.get();

            // Delete the social media platform
            repo.delete(existingSocialMedia);
        } else {
            // Handle the case where the social media platform with the given ID and provider is not found
            throw new RuntimeException("Social media platform not found for the given ID and provider");
        }
    }
}
