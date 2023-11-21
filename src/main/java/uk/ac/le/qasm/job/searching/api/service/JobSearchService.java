package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.http.ResponseEntity;

public interface JobSearchService {
    ResponseEntity<?> searchAll();
}
