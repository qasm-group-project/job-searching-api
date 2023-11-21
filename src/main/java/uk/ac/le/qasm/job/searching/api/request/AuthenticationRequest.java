package uk.ac.le.qasm.job.searching.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotEmpty(message = "The full username is required.")
    private String username;
    @NotEmpty(message = "The full password is required.")
    String password;
}
