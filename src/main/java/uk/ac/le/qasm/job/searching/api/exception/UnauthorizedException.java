package uk.ac.le.qasm.job.searching.api.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(HttpStatus.FORBIDDEN, "Wrong credentials");
    }
}
