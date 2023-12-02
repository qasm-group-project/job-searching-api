package uk.ac.le.qasm.job.searching.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex) {
        Map<String, Object> responseObj = new HashMap<>();
        responseObj.put("error", ex.getDescription());
        return ResponseEntity.status(ex.getHttpStatus()).body(responseObj);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Object> handleInsufficientAuthenticationException() {
        Map<String, Object> responseObj = new HashMap<>();
        responseObj.put("error", "User not authorized");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseObj);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception ex) {
        Map<String, Object> responseObj = new HashMap<>();
        responseObj.put("error", "Unknown error occurred, please try again later.");
        responseObj.put("details", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseObj);
    }
}
