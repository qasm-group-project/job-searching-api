package uk.ac.le.qasm.job.searching.api.exception;

public class JwtExtractionException extends RuntimeException{

    public JwtExtractionException(String message) {
        super(message);
    }
}
