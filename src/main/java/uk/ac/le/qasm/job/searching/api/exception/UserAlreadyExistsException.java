package uk.ac.le.qasm.job.searching.api.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException{
    public UserAlreadyExistsException() {
        super(HttpStatus.FORBIDDEN, "username already exist!");
    }
}
