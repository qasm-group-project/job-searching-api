package uk.ac.le.qasm.job.searching.api.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationRequest {
    @NotEmpty(message = "The full username is required.")
    private String username;
    @NotEmpty(message = "The full password is required.")
    String password;
}
