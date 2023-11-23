package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.entity.ProviderSocialMedia;
import uk.ac.le.qasm.job.searching.api.repository.ProviderRepository;

import java.util.List;

@Service
public class ProviderService {
    private ProviderRepository providerRepository;
    public Provider updateProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public List<ProviderSocialMedia> getAllSocialMediaPlatforms(Provider provider) {
        return provider.getSocialMediaPlatforms();
    }
}
