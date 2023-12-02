package uk.ac.le.qasm.job.searching.api.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.ac.le.qasm.job.searching.api.exception.BaseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        responseObj.put("error", "Failed to extract claim from JWT token");
        responseObj.put("details", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseObj);
    }
}
