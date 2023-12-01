package uk.ac.le.qasm.job.searching.api.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty(message = "The full username is required.")
    private String username;
    @NotEmpty(message = "The full password is required.")
    private String password;
    @NotEmpty(message = "The full company name is required.")
    private String company_name;
    @NotEmpty(message = "The full company contact number is required.")
    private String company_contact_number;
    @NotEmpty(message = "The full company location is required.")
    private String company_location;
}
