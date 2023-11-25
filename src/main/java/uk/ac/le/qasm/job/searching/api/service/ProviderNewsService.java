package uk.ac.le.qasm.job.searching.api.service;


import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.entity.ProviderNews;
import uk.ac.le.qasm.job.searching.api.entity.ProviderSocialMedia;
import uk.ac.le.qasm.job.searching.api.repository.ProviderNewsRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProviderNewsService {
    private final ProviderNewsRepository providerNewsRepository;

    public ProviderNewsService(ProviderNewsRepository providerNewsRepository) {
        this.providerNewsRepository = providerNewsRepository;
    }

    public ProviderNews saveProviderNews(ProviderNews newProviderNews) {
        return providerNewsRepository.save(newProviderNews);
    }

    public ProviderNews updateProviderNews(UUID newsId, ProviderNews updatedProviderNews) {
        Optional<ProviderNews> existingProviderNewsOptional = providerNewsRepository.findByIdAndProvider(
                newsId, updatedProviderNews.getProvider());

        if (existingProviderNewsOptional.isPresent()) {
            ProviderNews existingProviderNews = existingProviderNewsOptional.get();
            // Update fields as needed
            existingProviderNews.setTitle(updatedProviderNews.getTitle());
            existingProviderNews.setDescription(updatedProviderNews.getDescription());
            // Save the updated social media platform
            return providerNewsRepository.save(existingProviderNews);
        } else {
            // Handle the case where the social media platform with the given ID and provider is not found
            throw new RuntimeException("Provider news not found for the given ID and provider");
        }
    }

    public void deleteProviderNews(UUID newsId, Provider provider) {
        Optional<ProviderNews> existingProviderNewsOptional = providerNewsRepository.findByIdAndProvider(
                newsId, provider);

        if (existingProviderNewsOptional.isPresent()) {
            ProviderNews existingNews = existingProviderNewsOptional.get();

            // Delete the social media platform
            providerNewsRepository.delete(existingNews);
        } else {
            // Handle the case where the social media platform with the given ID and provider is not found
            throw new RuntimeException("Provider news not found for the given ID and provider");
        }
    }
}
