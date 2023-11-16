package uk.ac.le.qasm.job.searching.api.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.Enumeration.ApplicationStatus;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;
import uk.ac.le.qasm.job.searching.api.repository.JobApplicationRepository;
import uk.ac.le.qasm.job.searching.api.request.JobApplicationRequest;
import uk.ac.le.qasm.job.searching.api.service.JobApplicationService;
import java.util.UUID;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Override
    public ResponseEntity<?> create(JobApplicationRequest jobApplicationRequest) {
        var application = JobApplication.builder()
                .jobpost_uuid(UUID.fromString(jobApplicationRequest.getSeeker_uuid()))
                .seeker_uuid(UUID.fromString(jobApplicationRequest.getSeeker_uuid()))
                .applicationStatus(ApplicationStatus.PROCESSING)
                .build();
            return ResponseEntity.ok().body(jobApplicationRepository.save(application));
    }
}
