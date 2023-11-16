package uk.ac.le.qasm.job.searching.api.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.Enumeration.Role;
import uk.ac.le.qasm.job.searching.api.config.JwtService;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.repository.ProviderRepository;
import uk.ac.le.qasm.job.searching.api.request.AuthenticationRequest;
import uk.ac.le.qasm.job.searching.api.request.RegisterRequest;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ProviderRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<Object> register(RegisterRequest request){
        if (repository.findByUsername(request.getUsername()).isPresent()){
            Map<String, Object> responseObj = new HashMap<String, Object>();
            responseObj.put("message", "Username already exist!");
            responseObj.put("status", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<Object>(responseObj, HttpStatus.FORBIDDEN);
        }
        var provider = Provider.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .company_name(request.getCompany_name())
                .company_location(request.getCompany_location())
                .company_contact_number(request.getCompany_contact_number())
                .role(Role.PROVIDER)
                .build();
        repository.save(provider);
        var jwtToken = jwtService.generateToken(provider);
        Map<String, Object> responseObj = new HashMap<String, Object>();
        responseObj.put("message", "Provider Created successfully!");
        responseObj.put("status", HttpStatus.OK.value());
        responseObj.put("token", jwtToken);
        return new ResponseEntity<Object>(responseObj, HttpStatus.OK);
    }
    public ResponseEntity<Object> authenticate(AuthenticationRequest request) throws AuthenticationException{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    ));
            var provider =  repository.findByUsername(request.getUsername()).orElseThrow();
            var jwtToken = jwtService.generateToken(provider);
            Map<String, Object> responseObj = new HashMap<String, Object>();
            responseObj.put("message", "login successfully!");
            responseObj.put("status", HttpStatus.OK.value());
            responseObj.put("token", jwtToken);
            return new ResponseEntity<Object>(responseObj, HttpStatus.OK);
        }catch (BadCredentialsException badCredentialsException){
            Map<String, Object> responseObj = new HashMap<String, Object>();
            responseObj.put("message", "user not found!");
            responseObj.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<Object>(responseObj, HttpStatus.NOT_FOUND);
        }
    }
}
