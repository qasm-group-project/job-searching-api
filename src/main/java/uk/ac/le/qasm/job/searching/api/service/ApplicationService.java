package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobPostApplication;
import uk.ac.le.qasm.job.searching.api.entity.ProviderFeedback;
import uk.ac.le.qasm.job.searching.api.persistence.JobPostApplicationPersistence;

import java.util.UUID;

@Component
public class ApplicationService {

    private final JobPostApplicationPersistence jobPostApplicationPersistence;

    public ApplicationService(JobPostApplicationPersistence jobPostApplicationPersistence) {
        this.jobPostApplicationPersistence = jobPostApplicationPersistence;
    }

    public JobPostApplication updateProviderFeedback(UUID applicationId, ProviderFeedback providerFeedback) {
        JobPostApplication jobPostApplication = jobPostApplicationPersistence.findById(applicationId).orElseThrow();
        jobPostApplication.addFeedback(providerFeedback);

        return jobPostApplicationPersistence.save(jobPostApplication);
    }

    public JobPostApplication receiveFeedbackFromProvider(UUID applicationId){
        return jobPostApplicationPersistence.findById(applicationId).orElseThrow();
    }
}
