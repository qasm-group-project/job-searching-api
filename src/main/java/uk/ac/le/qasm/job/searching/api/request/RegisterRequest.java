package uk.ac.le.qasm.job.searching.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String company_name;
    private String company_contact_number;
    private String company_location;
}
