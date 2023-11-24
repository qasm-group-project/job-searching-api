package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.entity.SeekerSocialMedia;
import uk.ac.le.qasm.job.searching.api.repository.SeekerSocialMediaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeekerSocialMediaService {
    private final SeekerSocialMediaRepository repo;

    @Autowired
    public SeekerSocialMediaService(SeekerSocialMediaRepository repo) {
        this.repo = repo;
    }

    public SeekerSocialMedia saveSeekerSocialMedia(SeekerSocialMedia seekerSocialMedia) {
        if (isPlatformAlreadyExistsForSeeker(seekerSocialMedia.getSeeker(), seekerSocialMedia.getPlatform())) {
            throw new RuntimeException("Platform already exists for this seeker");
        }
        return repo.save(seekerSocialMedia);
    }

    public SeekerSocialMedia updateSeekerSocialMedia(UUID socialMediaId, SeekerSocialMedia updatedSocialMedia) {
        Optional<SeekerSocialMedia> existingSocialMediaOptional = repo.findByIdAndSeeker(
                socialMediaId, updatedSocialMedia.getSeeker());

        if (existingSocialMediaOptional.isPresent()) {
            SeekerSocialMedia existingSocialMedia = existingSocialMediaOptional.get();
            // Update fields as needed
            existingSocialMedia.setPlatform(existingSocialMedia.getPlatform());
            existingSocialMedia.setLink(updatedSocialMedia.getLink());
            // Save the updated social media platform
            return repo.save(existingSocialMedia);
        } else {
            // Handle the case where the social media platform with the given ID and seeker is not found
            throw new RuntimeException("Social media platform not found for the given ID and seeker");
        }
    }

    private boolean isPlatformAlreadyExistsForSeeker(JobSeeker seeker, String platform) {
        return repo.existsBySeekerAndPlatform(seeker, platform);
    }

    public void deleteSeekerSocialMedia(UUID socialMediaId, JobSeeker jobSeeker) {
        Optional<SeekerSocialMedia> existingSocialMediaOptional = repo.findByIdAndSeeker(
                socialMediaId, jobSeeker);

        if (existingSocialMediaOptional.isPresent()) {
            SeekerSocialMedia existingSocialMedia = existingSocialMediaOptional.get();

            // Delete the social media platform
            repo.delete(existingSocialMedia);
        } else {
            // Handle the case where the social media platform with the given ID and seeker is not found
            throw new RuntimeException("Social media platform not found for the given ID and seeker");
        }
    }

    public List<SeekerSocialMedia> getAllSocialMediaPlatforms(JobSeeker seeker) {
        return repo.findAllBySeeker(seeker);
    }
}
