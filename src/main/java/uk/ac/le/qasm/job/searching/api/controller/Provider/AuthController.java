package uk.ac.le.qasm.job.searching.api.controller.Provider;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.le.qasm.job.searching.api.Service.AuthenticationService;
import uk.ac.le.qasm.job.searching.api.request.AuthenticationRequest;
import uk.ac.le.qasm.job.searching.api.request.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth/provider")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request){
        return authenticationService.register(request);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request){
        return authenticationService.authenticate(request);
    }
}
