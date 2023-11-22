package uk.ac.le.qasm.job.searching.api.exception;

import org.springframework.http.HttpStatus;

public class JobPostNotFoundException extends BaseException{
    public JobPostNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Job post not found");
    }
}
