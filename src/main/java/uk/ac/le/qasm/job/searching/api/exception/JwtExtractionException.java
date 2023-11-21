package uk.ac.le.qasm.job.searching.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;


public class JwtExtractionException extends RuntimeException{

    public JwtExtractionException(String message) {
        super(message);
    }
}
