package uk.ac.le.qasm.job.searching.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.enums.Role;
import uk.ac.le.qasm.job.searching.api.config.JwtService;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.repository.ProviderRepository;
import uk.ac.le.qasm.job.searching.api.entity.AuthenticationRequest;
import uk.ac.le.qasm.job.searching.api.entity.RegisterRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ProviderRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public String register(RegisterRequest request){
        if (repository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
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
        return jwtService.generateToken(provider);
    }
    public String authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );
        var provider = repository.findByUsername(request.getUsername()).orElseThrow();
        return jwtService.generateToken(provider);
    }

}
