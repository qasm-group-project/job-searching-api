package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.http.ResponseEntity;
import uk.ac.le.qasm.job.searching.api.request.JobApplicationRequest;

public interface JobApplicationService {
    ResponseEntity<?> create(JobApplicationRequest jobApplication);
}
